package com.xipsoft.hotelrestapi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xipsoft.hotelrestapi.domain.HotelEntity;
import com.xipsoft.hotelrestapi.resource.Amenity;
import com.xipsoft.hotelrestapi.resource.AmenityList;
import com.xipsoft.hotelrestapi.resource.HotelSearchParam;
import com.xipsoft.hotelrestapi.resource.Room;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest()
@AutoConfigureMockMvc
public class HotelRestControllerTest {

    @Autowired
    private MockMvc mvc;


    @Autowired
     JdbcTemplate jdbcTemplate;

    @Before
    public void resetState(){

        JdbcTestUtils.deleteFromTables(jdbcTemplate, "hotel_amenities");
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "room_amenities");
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "amenity_mst");
        JdbcTestUtils.deleteFromTables(jdbcTemplate,  "rooms");
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "hotels");
        jdbcTemplate.update("ALTER TABLE hotels ALTER COLUMN hotel_id RESTART WITH 1");
        jdbcTemplate.update("ALTER TABLE rooms ALTER COLUMN room_id RESTART WITH 1");
        jdbcTemplate.update("ALTER TABLE amenity_mst ALTER COLUMN amenity_id RESTART WITH 1");
        jdbcTemplate.update("ALTER TABLE room_amenities ALTER COLUMN room_ame_id RESTART WITH 1");
        jdbcTemplate.update("ALTER TABLE hotel_amenities ALTER COLUMN hotel_ame_id RESTART WITH 1");
    }


    private void createManyHotels(int count) throws Exception {
        for(int i=0;i<count;i++){
            HotelEntity hotelEntity = new HotelEntity();
            hotelEntity.setName("name"+i);
            hotelEntity.setDescription("description"+i);
            hotelEntity.setCityCode("c"+i);
            String json = toJSon(hotelEntity);
            mvc.perform(post("/api/hotels")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(json))
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                    .andExpect(status().isCreated());
        }
    }

    private String toJSon(Object object) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(object);
    }


    private AmenityList createAmenities(int count) {
        List<Amenity> amenities = new ArrayList<>();
        for(int i=0;i<count;i++){
            Amenity amenity = new Amenity();
            amenity.setShortDesc("cod"+i);
            amenity.setDescription("desc"+i);
            amenity.setChargeable(true);
            amenity.setAmount(BigDecimal.valueOf(100));
            amenities.add(amenity);
        }
        return new AmenityList(amenities);
    }



    private void createAmenitiesForHotel(int hotelId, int count) throws Exception {
        AmenityList amenityList = createAmenities(count);
        mvc.perform(post("/api/hotels/"+hotelId+"/amenities")
                .contentType(MediaType.APPLICATION_JSON).content(toJSon(amenityList)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$", hasSize(count)))
                .andExpect(status().isCreated());
    }

    private void createAmenitiesForRoom(int roomId, int count) throws Exception {
        AmenityList amenityList = createAmenities(count);
        mvc.perform(post("/api/rooms/"+roomId+"/amenities")
                .contentType(MediaType.APPLICATION_JSON).content(toJSon(amenityList)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$", hasSize(count)))
                .andExpect(status().isCreated());
    }

    private void createRooms(int hotelId,int count) throws Exception {
        for(int i=0;i<count;i++){
            Room room = new Room();
            room.setDescription("description"+i);
            String json = toJSon(room);
            mvc.perform(post("/api/hotels/"+hotelId+"/rooms")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(json))
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                    .andExpect(status().isCreated());
        }
    }
    // Start Tests

    @Test
    public void testCreateHotel() throws Exception {
        HotelEntity hotelEntity = new HotelEntity();
        hotelEntity.setName("Hotel 1");
        hotelEntity.setDescription("Hotel Description 1");
        hotelEntity.setCityCode("Cod");

        String json = toJSon(hotelEntity);
        mvc.perform(post("/api/hotels")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Hotel 1")))
                .andExpect(jsonPath("$.description", is("Hotel Description 1")))
                .andExpect(jsonPath("$.cityCode", is("Cod")))
                .andExpect(status().isCreated());
    }

    @Test
    public void testCreateHotel_With_Invalid_Data() throws Exception {
        HotelEntity hotelEntity = new HotelEntity();

        String json = toJSon(hotelEntity);
        mvc.perform(post("/api/hotels")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.message", is("Validation failed for hotel")))
                .andExpect(jsonPath("$.details.cityCode", is("City code cannot be null")))
                .andExpect(jsonPath("$.details.name", is("Hotel name cannot be null")))
                .andExpect(jsonPath("$.details.description", is("Hotel description cannot be null")))
                .andExpect(jsonPath("$.path", is("/api/hotels")))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetHotels() throws Exception {
      createManyHotels(10);

        mvc.perform(get("/api/hotels")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.content", hasSize(10)))
                .andExpect(status().isOk());
    }

    @Test
    public void testSearchHotels() throws Exception {
        createManyHotels(10);
        HotelSearchParam param = new HotelSearchParam();
        param.setName("3");
        mvc.perform(post("/api/hotels/search")
                .contentType(MediaType.APPLICATION_JSON).content(toJSon(param)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(status().isOk());
    }

    @Test
    public void testSearchHotelsPaging() throws Exception {
        createManyHotels(50);
        HotelSearchParam param = new HotelSearchParam();
        param.setName("name");
        mvc.perform(post("/api/hotels/search?size=10")
                .contentType(MediaType.APPLICATION_JSON).content(toJSon(param)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.content", hasSize(10)))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteHotel() throws Exception {
        createManyHotels(1);

        mvc.perform(delete("/api/hotels/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testAddRoomToHotel() throws Exception {
        createManyHotels(1);

        Room room = new Room();
        room.setDescription("Description");
        mvc.perform(post("/api/hotels/1/rooms")
                .contentType(MediaType.APPLICATION_JSON).content(toJSon(room)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.description", is("Description")))
                .andExpect(jsonPath("$.hotelId", is(1)))
                .andExpect(status().isCreated());
    }


    @Test
    public void testAddRoomToHotel_With_Invalid_Data() throws Exception {
        createManyHotels(1);

        Room room = new Room();
        mvc.perform(post("/api/hotels/1/rooms")
                .contentType(MediaType.APPLICATION_JSON).content(toJSon(room)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.message", is("Validation failed for room")))
                .andExpect(jsonPath("$.details.description", is("Room description cannot be null")))
                .andExpect(jsonPath("$.path", is("/api/hotels/1/rooms")))
                .andExpect(status().isBadRequest());
    }
    @Test
    public void testAddRoomToHotel_With_Missing_Hotel() throws Exception {
        Room room = new Room();
        room.setDescription("Description");
        mvc.perform(post("/api/hotels/1/rooms")
                .contentType(MediaType.APPLICATION_JSON).content(toJSon(room)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.message", is("Hotel not found")))
                .andExpect(jsonPath("$.details", is("Hotel not found with id 1")))
                .andExpect(jsonPath("$.path", is("/api/hotels/1/rooms")))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testAddAmenitiesToHotel() throws Exception {
        createManyHotels(1);

        AmenityList amenityList = createAmenities(3);
        mvc.perform(post("/api/hotels/1/amenities")
                .contentType(MediaType.APPLICATION_JSON).content(toJSon(amenityList)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].chargeable", is(true)))
                .andExpect(jsonPath("$[0].amount", is(100)))
                .andExpect(jsonPath("$[0].amenity.id", is(1)))
                .andExpect(jsonPath("$[0].amenity.shortDesc", is("cod0")))
                .andExpect(jsonPath("$[0].amenity.description", is("desc0")))
                .andExpect(status().isCreated());
    }


    @Test
    public void testAddAmenitiesToHotel_With_Invalid_Data() throws Exception {
        createManyHotels(1);

        AmenityList amenityList = createAmenities(3);
        amenityList.getItems().get(0).setShortDesc(null);
        mvc.perform(post("/api/hotels/1/amenities")
                .contentType(MediaType.APPLICATION_JSON).content(toJSon(amenityList)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.message", is("Validation failed for amenityList")))
                .andExpect(jsonPath("$.path", is("/api/hotels/1/amenities")))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testAddAmenitiesToHotel_With_Missing_Hotel() throws Exception {
        AmenityList amenityList = createAmenities(3);
        mvc.perform(post("/api/hotels/1/amenities")
                .contentType(MediaType.APPLICATION_JSON).content(toJSon(amenityList)))
                .andExpect(jsonPath("$.message", is("Hotel not found")))
                .andExpect(jsonPath("$.details", is("Hotel not found with id 1")))
                .andExpect(jsonPath("$.path", is("/api/hotels/1/amenities")))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testDeleteHotelAmenity() throws Exception {
        createManyHotels(1);
        createAmenitiesForHotel(1,3);
        mvc.perform(delete("/api/hotels/amenities/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testDeleteHotelAmenity_With_Missing_HotelAmenity() throws Exception {
        mvc.perform(delete("/api/hotels/amenities/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message", is("No class com.xipsoft.hotelrestapi.domain.HotelAmenityEntity entity with id 1 exists!")))
                .andExpect(jsonPath("$.details", is("No class com.xipsoft.hotelrestapi.domain.HotelAmenityEntity entity with id 1 exists!")))
                .andExpect(jsonPath("$.path", is("/api/hotels/amenities/1")))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetRoomsForHotel() throws Exception {
        createManyHotels(1);
        createRooms(1,5);

        mvc.perform(get("/api/hotels/1/rooms")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$", hasSize(5)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].description", is("description0")))
                .andExpect(jsonPath("$[0].hotelId", is(1)))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteRoom() throws Exception {
        createManyHotels(1);
        createRooms(1,5);

        mvc.perform(delete("/api/rooms/1")
                .contentType(MediaType.APPLICATION_JSON))

                .andExpect(status().isNoContent());
    }


    @Test
    public void testDeleteRoom_With_Missing_Room() throws Exception {

        mvc.perform(delete("/api/rooms/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message", is("No class com.xipsoft.hotelrestapi.domain.RoomEntity entity with id 1 exists!")))
                .andExpect(jsonPath("$.details", is("No class com.xipsoft.hotelrestapi.domain.RoomEntity entity with id 1 exists!")))
                .andExpect(jsonPath("$.path", is("/api/rooms/1")))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testAddAmenitiesToRoom() throws Exception {
        createManyHotels(1);
        createRooms(1,2);
        AmenityList amenityList = createAmenities(3);
        mvc.perform(post("/api/rooms/2/amenities")
                .contentType(MediaType.APPLICATION_JSON).content(toJSon(amenityList)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].chargeable", is(true)))
                .andExpect(jsonPath("$[0].amount", is(100)))
                .andExpect(jsonPath("$[0].amenity.id", is(1)))
                .andExpect(jsonPath("$[0].amenity.shortDesc", is("cod0")))
                .andExpect(jsonPath("$[0].amenity.description", is("desc0")))
                .andExpect(status().isCreated());
    }

    @Test
    public void testAddAmenitiesToRoom_With_Invalid_Data() throws Exception {
        createManyHotels(1);
        createRooms(1,2);
        AmenityList amenityList = createAmenities(3);
        amenityList.getItems().get(0).setShortDesc(null);
        mvc.perform(post("/api/rooms/2/amenities")
                .contentType(MediaType.APPLICATION_JSON).content(toJSon(amenityList)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.message", is("Validation failed for amenityList")))
                .andExpect(jsonPath("$.path", is("/api/rooms/2/amenities")))
                .andExpect(status().isBadRequest());
    }


    @Test
    public void testGetAmenitiesForRoom() throws Exception {
        createManyHotels(1);
        createRooms(1,2);
       createAmenitiesForRoom(2,5);
        mvc.perform(get("/api/rooms/2/amenities")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$", hasSize(5)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].chargeable", is(true)))
                .andExpect(jsonPath("$[0].amount", is(100.00)))
                .andExpect(jsonPath("$[0].amenity.id", is(1)))
                .andExpect(jsonPath("$[0].amenity.shortDesc", is("cod0")))
                .andExpect(jsonPath("$[0].amenity.description", is("desc0")))
                .andExpect(status().isOk());
    }


    @Test
    public void testDeleteRoomAmenity() throws Exception {
        createManyHotels(1);
        createRooms(1,1);
        createAmenitiesForRoom(1,3);
        mvc.perform(delete("/api/rooms/amenities/2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testDeleteRoomAmenity_With_Missing_RoomAmenity() throws Exception {
        mvc.perform(delete("/api/rooms/amenities/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message", is("No class com.xipsoft.hotelrestapi.domain.RoomAmenityEntity entity with id 1 exists!")))
                .andExpect(jsonPath("$.details", is("No class com.xipsoft.hotelrestapi.domain.RoomAmenityEntity entity with id 1 exists!")))
                .andExpect(jsonPath("$.path", is("/api/rooms/amenities/1")))
                .andExpect(status().isNotFound());
    }
}
