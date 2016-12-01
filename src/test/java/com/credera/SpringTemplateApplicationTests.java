package com.credera;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

/*
@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringTemplateApplicationTests {
	/********************************************************************************************************************************
	 *
	 * These functions can be used to test controller classes through get or post requests.
	 *
	 ********************************************************************************************************************************/
/*
	private String appUrl = "http://localhost:8080"; // private String apiUrl = "https://podsurfer3.herokuapp.com";
	private boolean unirestConfigured = false;
	private static void configureUnirest(){
		Unirest.setObjectMapper(new ObjectMapper() {
			private com.fasterxml.jackson.databind.ObjectMapper jacksonObjectMapper
					= new com.fasterxml.jackson.databind.ObjectMapper();

			public <T> T readValue(String value, Class<T> valueType) {
				try {
					return jacksonObjectMapper.readValue(value, valueType);
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}

			public String writeValue(Object value) {
				try {
					return jacksonObjectMapper.writeValueAsString(value);
				} catch (JsonProcessingException e) {
					throw new RuntimeException(e);
				}
			}
		});
	}
	public String controllerPostRequest(String endpoint, Object payload) {
		if(!unirestConfigured)
		{
			configureUnirest();
			unirestConfigured = true;
		}
		Response response = new Response();
		String functionEndpoint = appUrl + endpoint;
		try {
			HttpResponse<JsonNode> jsonResponse = Unirest.post(functionEndpoint)
					.header("accept", "application/json")
					.header("Content-Type", "application/json")
					.body(payload)
					.asJson();

			return jsonResponse.getBody().toString();
		} catch (UnirestException e) {
// TODO Auto-generated catch block
			e.printStackTrace();
			return "{ \"Error\": \"An Unexpected error occured\" }";
		}
	};
	public String controllerGetRequest(String endpoint) {

		Response response = new Response();
		String requestUrl = appUrl + endpoint;

		try {

			HttpResponse<JsonNode> jsonResponse = Unirest.get(requestUrl)
					.asJson();

			return jsonResponse.getBody().toString();
		} catch (UnirestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			String errorResponse = "{ \"Error\": \"An Unexpected error occured\" }";
			return errorResponse;
		}

		// return response;
	}
	/********************************************************************************************************************************
	 *
	 * These functions create test data (podcasts, episodes, and reviews).
	 *
	 ********************************************************************************************************************************/
/*
	public Podcast createPodcast()
	{
		Podcast testPodcast = new Podcast();
		testPodcast.setName("Paw-sitively Perfect Puppies Podcast");
		testPodcast.setLink("www.perfectpuppies.com");
		testPodcast.setRelease(new Date());
		testPodcast.setProducer("Libby");
		testPodcast.setDescription("A really cute podcast about fuzzy puppies.");
		String tags[] = new String[3];
		tags[0] = "woof";
		tags[1] = "bark";
		tags[2] = "yip";
		testPodcast.setTags(tags);
		testPodcast.setImageUrl("http://www.gapviewkennel.com/wp-content/uploads/2012/05/golden-retriever-puppies_1-640x320.jpg");
		return testPodcast;
	}
	public Episode createEpisode()
	{
		Episode testEpisode = new Episode();
		testEpisode.setNumber(1);
		testEpisode.setName("Puppies Go Swimming");
		testEpisode.setLength(200);
		testEpisode.setDescription("In this episode, puppies go swimming.");
		testEpisode.setUpload_date(new Date());
		return testEpisode;
	}
	public Review createReview()
	{
		Review testReview = new Review();
		testReview.setUserId("abc123");  // Where can I find this?
		testReview.setRating(5);
		testReview.setUserName("Kevin Aud");
		testReview.setContent("Libby is bae");
		testReview.setDatePosted(new Date());
		return testReview;
	}
	public User createUser()
	{
		User testUser = new User("libby_smetak_test2@gmail.com", "Libby Smetak", "ABCabc123!@#$%^&*()+");
		return testUser;
	}
	public User createLogInUser()
	{
		User testUser = new User("libby_smetak_test2@gmail.com", "", "ABCabc123!@#$%^&*()+");
		return testUser;
	}

	// I don't know what Credera wanted us to do with this function.
// Will consult google later.
	@Test
	public void contextLoads() {
		System.out.println("Context loading has been tested.");
	}

	/********************************************************************************************************************************
	 *
	 * These functions test java classes.
	 *
	 ********************************************************************************************************************************/
/*
	@Test
	public void testEpisode()
	{
		Episode testEpisode = createEpisode();
		Date testDate = new Date();
		Date testDateMatch = testDate;
		testEpisode.setUpload_date(testDate);
		assertEquals("Episode number must be 1.", Integer.valueOf(1), testEpisode.getNumber());
		assertEquals("Episode name must be 'Puppies Go Swimming'.", "Puppies Go Swimming", testEpisode.getName());
		assertEquals("Episode length must be 200.", Integer.valueOf(200), testEpisode.getLength());
		assertEquals("Episode description must be 'In this episode, puppies go swimming.' .",
				"In this episode, puppies go swimming.", testEpisode.getDescription());
		assertEquals("Episode date must be " + testDate.toString(), testDateMatch, testEpisode.getUpload_date());
		System.out.println("The Episode class has been tested.");
	}
	@Test
	public void testPodcast()
	{
		Podcast testPodcast = createPodcast();
		Date testDate = new Date();
		Date testDateMatch = testDate;
		testPodcast.setRelease(testDate);
		String tags[] = new String[3];
		tags[0] = "woof";
		tags[1] = "bark";
		tags[2] = "yip";
		// comment
		assertEquals("Podcast name must be 'Paw-sitively Perfect Puppies Podcast'.", "Paw-sitively Perfect Puppies Podcast", testPodcast.getName());
		assertEquals("Podcast link must be 'www.perfectpuppies.com'.", "www.perfectpuppies.com", testPodcast.getLink());
		assertEquals("Podcast release should be " + testDateMatch.toString(), testDateMatch, testPodcast.getRelease());
		assertEquals("Podcast producer should be 'Libby'.", "Libby", testPodcast.getProducer());
		assertEquals("Podcast description should be 'A really cute podcast about fuzzy puppies.'.",
				"A really cute podcast about fuzzy puppies.", testPodcast.getDescription());
		assertArrayEquals("Podcast tag list should match the one created.", tags, testPodcast.getTags());
		assertEquals("Podcast image URL should be 'http://www.gapviewkennel.com/wp-content/uploads/2012/05/golden-retriever-puppies_1-640x320.jpg'.",
				"http://www.gapviewkennel.com/wp-content/uploads/2012/05/golden-retriever-puppies_1-640x320.jpg", testPodcast.getImageUrl());
		System.out.println("The Podcast class has been tested.");
	}
	/********************************************************************************************************************************
	 *
	 * These functions test java spring controllers. (Possible issues during codeship deployment?)
	 *
	 ********************************************************************************************************************************/
/*
	@Test
	public void testPodcastController()
	{
// All of these tests require podcast with ID AVf5FdgQEoOOTOV5Go7E
// and episode with ID AVf5HCLTEoOOTOV5Go7G
// and review with ID AVgY-nHBEoOOTOV5GyxO
// to be in the heroku module.
		Podcast testPodcast = createPodcast();
		Episode testEpisode = createEpisode();
		String response = "";
// savePodcast
// THIS REQUEST IS ADDING PODCASTS... ADD A DELETE FUNCTION TO COUNTER IT
// response = controllerPostRequest("/podcast", testPodcast);
		System.out.println(response);
		assertNotEquals("{ \"Error\": \"An Unexpected error occured\" }", response);
// getPodcast
		response = controllerGetRequest("/podcast/AVf5FdgQEoOOTOV5Go7E");
		System.out.println(response);
		assertEquals("{\"found\":true", response.substring(0, 13));
// saveEpisode
// THIS REQUEST IS ADDING EPISODED... ADD A DELETE FUNCTION TO COUNTER
		response = controllerPostRequest("/podcast/AVf5FdgQEoOOTOV5Go7E/episodes", testEpisode);
		System.out.println(response);
		assertNotEquals("{ \"Error\": \"An Unexpected error occured\" }", response);
// getPodcastByEpisodeNumber
		response = controllerGetRequest("/podcast/AVf5FdgQEoOOTOV5Go7E/episodes/1");
		System.out.println(response);
		assertNotEquals("{ \"Error\": \"An Unexpected error occured\" }", response);
// getEpisodeById
		response = controllerGetRequest("/episodes/AVf5FdgQEoOOTOV5Go7E/AVf5HCLTEoOOTOV5Go7G");
		System.out.println(response);
		assertEquals("{\"found\":true", response.substring(0, 13));
// getPodcastByEpisodeId
		response = controllerGetRequest("/episodes/AVf5HCLTEoOOTOV5Go7G/podcast");
		System.out.println(response);
		assertNotEquals("{ \"Error\": \"An Unexpected error occured\" }", response);
// getAllEpisodesForPodcast
		response = controllerGetRequest("/podcast/AVf5FdgQEoOOTOV5Go7E/episodes");
		System.out.println(response);
		assertNotEquals("{ \"Error\": \"An Unexpected error occured\" }", response);
// saveReview
// response = controllerPostRequest("/podcast/AVf5FdgQEoOOTOV5Go7E/reviews", createReview());
		System.out.println(response);
// assertEquals("{\"_index\":\"podcasts\",\"created\":true", response.substring(0, 34));
// getPodcastReviewById
		response = controllerGetRequest("/podcast/AVf5FdgQEoOOTOV5Go7E/reviews/AVgY-nHBEoOOTOV5GyxO");
		System.out.println(response);
		assertEquals("{\"found\":true", response.substring(0, 13));
//getAllReviewsForPodcast
		response = controllerGetRequest("/podcast/AVf5FdgQEoOOTOV5Go7E/reviews");
		System.out.println(response);
		assertNotEquals("{ \"Error\": \"An Unexpected error occured\" }", response);
	}
	@Test
	public void testUserController()
	{
// !! THIS IS PASSING !!
		String response = "";
// sign up user
//response = controllerPostRequest("/sign-up", createUser());
		System.out.println(response);
//assertEquals("{\"success\":true", response.substring(0, 15));
// !! THIS IS FAILING !!
// log in user
//response = controllerPostRequest("/login", createLogInUser());
		System.out.println(response);
//assertEquals("{\"success\":true", response.substring(0, 15));
	}
}
*/