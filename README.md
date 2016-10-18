# PodSurfer - Group 3

## Build Status
![Build Status](https://codeship.com/projects/3c834dd0-5f38-0134-620d-1efa85447d7f/status?branch=master)

## Group Members
* Kevin Aud
* Libby Smetak
* Jack Swenson
* Connor Shride
* Quoc-bao Huynh

# Documentation

## API endpoints


# Podcasts

```json
{
 "name": "String",
 "link": "String",
 "release": "Date (ISO 8601 format)",
 "producer": "String",
 "description": "String",
 "tags": "String []",
 "imageUrl": "String"
}
```

* **/podcast**
 * **POST** - Add a new podcast

* **/podcast/{podcastId}**
 * **GET** - Retrieve all information for the podcast with the given id
 * **POST** - Update the information of the podcast with given id


# Podcast Episodes


```json
{
 "number": "Int",
 "name": "String",
 "length": "Int (number of seconds)",
 "description": "String",
 "upload_date": "Date (ISO 8601 format)"
}
```

* **/podcast/{podcastId}/episodes**
 * **POST** - Add a new episode to the podcast with the given id
 * **GET** - Retrieve all episodes of the podcast with the given id

* **/podcast/{podcastId}/episodes/{episodeNumber}**
 * **GET** - Retrieve all information for an individual episode of a podcast.
 * **POST** - Update the information for an individual episode of a podcast
 * **The second parameter is the episode number, not the episode Id.** A query to /podcast/1/episodes/2 would return the second episode of the podcast with an id of 1, however that episodes unique identifier may not be (and probably isn't) equal to 2. Each episode has an "id"  as well as a "number." **An episode's "id" distinguishes it from every other episode in the database, including episodes from other podcasts, whereas an episode's number only distinguishes it from other episodes of it's own podcast**

 # Search


 * **/search**
    * **POST** - search both podcasts and episodes
        * request body format:
          ```
          {
            "query": "<insert search term(s) here>"
          }
          ```
    * **GET** - returns all podcasts and all episodes