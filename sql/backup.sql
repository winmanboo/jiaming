-- MySQL dump 10.13  Distrib 8.0.31, for macos13.0 (arm64)
--
-- Host: 127.0.0.1    Database: jiaming
-- ------------------------------------------------------
-- Server version	8.0.31

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `oauth2_authorization`
--

DROP TABLE IF EXISTS `oauth2_authorization`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `oauth2_authorization` (
  `id` varchar(100) COLLATE utf8mb4_general_ci NOT NULL,
  `registered_client_id` varchar(100) COLLATE utf8mb4_general_ci NOT NULL,
  `principal_name` varchar(200) COLLATE utf8mb4_general_ci NOT NULL,
  `authorization_grant_type` varchar(100) COLLATE utf8mb4_general_ci NOT NULL,
  `authorized_scopes` varchar(1000) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `attributes` blob,
  `state` varchar(500) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `authorization_code_value` blob,
  `authorization_code_issued_at` timestamp NULL DEFAULT NULL,
  `authorization_code_expires_at` timestamp NULL DEFAULT NULL,
  `authorization_code_metadata` blob,
  `access_token_value` blob,
  `access_token_issued_at` timestamp NULL DEFAULT NULL,
  `access_token_expires_at` timestamp NULL DEFAULT NULL,
  `access_token_metadata` blob,
  `access_token_type` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `access_token_scopes` varchar(1000) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `oidc_id_token_value` blob,
  `oidc_id_token_issued_at` timestamp NULL DEFAULT NULL,
  `oidc_id_token_expires_at` timestamp NULL DEFAULT NULL,
  `oidc_id_token_metadata` blob,
  `refresh_token_value` blob,
  `refresh_token_issued_at` timestamp NULL DEFAULT NULL,
  `refresh_token_expires_at` timestamp NULL DEFAULT NULL,
  `refresh_token_metadata` blob,
  `token` varchar(1000) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '自定义 token',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `oauth2_authorization`
--

LOCK TABLES `oauth2_authorization` WRITE;
/*!40000 ALTER TABLE `oauth2_authorization` DISABLE KEYS */;
INSERT INTO `oauth2_authorization` VALUES ('1fdcb4b4-fb6d-49c9-8109-5bac6431bcba','jiaming-default-id','default','captcha',NULL,_binary '{\"@class\":\"java.util.Collections$UnmodifiableMap\"}',NULL,NULL,NULL,NULL,NULL,_binary 'eyJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJ3aW5tYW5ib28iLCJhdWQiOiJkZWZhdWx0IiwidWlkIjoxLCJuYmYiOjE3MDI0NTk2NDcsImlzcyI6Imh0dHA6Ly8xOTIuMTY4LjEwLjI6OTAwMCIsImFkbWluIjp0cnVlLCJleHAiOjE3MDI0NjMyNDcsImlhdCI6MTcwMjQ1OTY0NywiYXV0aG9yaXRpZXMiOltdLCJ0ZW5hbnQiOjAsInVzZXJuYW1lIjoid2lubWFuYm9vIn0.jZrLKvyvtN6mYe1N-TmQ36usUTJw2W4mw8kMvgurZHGMdcnWz-6RYhlzaahn8gqvGiyF94MOMBGBoSNKuPCDWHdDCR6c--HlV8cQKcXhx62F51QGrOb4z8x5CC98dMrInHNTl7t7dns05LtG58xA70_G_9KGmjzcOPrLxAttbpfACzELTRt3EdX-9fAkpu8LWGw2pIcgHbQamqVgCHCBlDKwj649oZy3CSpiOy1mem8OTuHbVZyNZ5LnIvY-oPrad3lPSXQlPXJq-X2SvFkABlJw7K-ljtomGsjJc8Q6-7Clck_puDu8gVHCLtwsb5sIiVadAfsAQp_lJpZdV15Umg','2023-12-13 09:27:27','2023-12-13 10:27:27',_binary '{\"@class\":\"java.util.Collections$UnmodifiableMap\",\"metadata.token.claims\":{\"@class\":\"java.util.Collections$UnmodifiableMap\",\"sub\":\"winmanboo\",\"aud\":[\"java.util.Collections$SingletonList\",[\"default\"]],\"uid\":[\"java.lang.Long\",1],\"nbf\":[\"java.time.Instant\",1702459647.254421000],\"iss\":[\"java.net.URL\",\"http://192.168.10.2:9000\"],\"admin\":true,\"exp\":[\"java.time.Instant\",1702463247.254421000],\"iat\":[\"java.time.Instant\",1702459647.254421000],\"authorities\":[\"java.util.ImmutableCollections$ListN\",[]],\"tenant\":[\"java.lang.Long\",0],\"username\":\"winmanboo\"},\"metadata.token.invalidated\":false}','Bearer',NULL,NULL,NULL,NULL,NULL,_binary 'VKXLfUKbxpzjrGeU2t2KZu4hU6LaFlA33LmmNO9GHLetUpMDy8NAlnJudu30Ed3NU6Q6DL2aqi1JdPke9zkmz5NJzBY89YEUbxde-AS55q7E_EfIgwEPaDujMuAdx9Cb','2023-12-13 09:27:27','2023-12-13 21:27:27',_binary '{\"@class\":\"java.util.Collections$UnmodifiableMap\",\"metadata.token.invalidated\":false}','7faf1c7808174bf98ab7791c630e8f1f'),('772a2a36-bf4c-4f6a-ae15-03ab2dc0712c','jiaming-default-id','default','captcha',NULL,_binary '{\"@class\":\"java.util.Collections$UnmodifiableMap\"}',NULL,NULL,NULL,NULL,NULL,_binary 'eyJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJ3aW5tYW5ib28iLCJhdWQiOiJkZWZhdWx0IiwidWlkIjoxLCJuYmYiOjE3MDMxNTIyODIsImlzcyI6Imh0dHA6Ly8xOTIuMTY4LjEwLjI6OTAwMCIsImFkbWluIjp0cnVlLCJleHAiOjE3MDMxNTU4ODIsImlhdCI6MTcwMzE1MjI4MiwiYXV0aG9yaXRpZXMiOltdLCJ0ZW5hbnQiOjAsInVzZXJuYW1lIjoid2lubWFuYm9vIn0.A5Jkvy8SxkFKgr8dftGg4wPGs4H_nkPkzPCvoJHLMyZ-a3qS-lRVi5kjhnaLQNlzS1pL7-rhAHmzfKX07Vi7yWR7N6EGh9-VpSR0uia0-sKyC3ClMcGE4pUnDth7mfozIkASVb-NZ8V46ja9witta2R5D6KXJfyJ6o8dvd9nQC3OphgRKyhmSbMYkP7yP8tvXygaDKLwIATWaIPC5h5XCyZf5oy5EI0r91koswANF_V7-2kllTNxipcVTWay6hItY08PPB71KThOx6YtJgr_VVSYoMPReV_HAuUZ1jDJdDJSZhnGh9UsvfY1ka7gTj4c3Vk0hHHAy1kI2I7TEzYyvg','2023-12-21 09:51:22','2023-12-21 10:51:22',_binary '{\"@class\":\"java.util.Collections$UnmodifiableMap\",\"metadata.token.claims\":{\"@class\":\"java.util.Collections$UnmodifiableMap\",\"sub\":\"winmanboo\",\"aud\":[\"java.util.Collections$SingletonList\",[\"default\"]],\"uid\":[\"java.lang.Long\",1],\"nbf\":[\"java.time.Instant\",1703152282.443176000],\"iss\":[\"java.net.URL\",\"http://192.168.10.2:9000\"],\"admin\":true,\"exp\":[\"java.time.Instant\",1703155882.443176000],\"iat\":[\"java.time.Instant\",1703152282.443176000],\"authorities\":[\"java.util.ImmutableCollections$ListN\",[]],\"tenant\":[\"java.lang.Long\",0],\"username\":\"winmanboo\"},\"metadata.token.invalidated\":false}','Bearer',NULL,NULL,NULL,NULL,NULL,_binary 'VmxU8yCYKlSsglZWAVJ-r0l6zVf2MMV2LNaXfuJxzMmXNg1JeamiWJI0SaPVrft9_vNvraXwZ9AqkhAM3OykBCQuBo8qbu4LwPQD65s6H72Q9euWuUEfB2VqdDqHNaUR','2023-12-21 09:51:22','2023-12-21 21:51:22',_binary '{\"@class\":\"java.util.Collections$UnmodifiableMap\",\"metadata.token.invalidated\":false}','319f4bafb6164838ab8015c13cd196b8'),('8feb6f7e-0e17-43de-8d08-e9fb10049c7d','jiaming-default-id','default','captcha',NULL,_binary '{\"@class\":\"java.util.Collections$UnmodifiableMap\"}',NULL,NULL,NULL,NULL,NULL,_binary 'eyJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJ3aW5tYW5ib28iLCJhdWQiOiJkZWZhdWx0IiwidWlkIjoxLCJuYmYiOjE3MDIzODk3NjcsImlzcyI6Imh0dHA6Ly8xOTIuMTY4LjEwLjI6OTAwMCIsImFkbWluIjp0cnVlLCJleHAiOjE3MDIzOTMzNjcsImlhdCI6MTcwMjM4OTc2NywiYXV0aG9yaXRpZXMiOltdLCJ0ZW5hbnQiOjAsInVzZXJuYW1lIjoid2lubWFuYm9vIn0.LSnxUlJshc685sXtnwD0bE_J9alGTxyvkXgHWp2KrbwqJQ8vRzePs--75CPtmVvNTN9Ll1h_gM9oLp38zQ0LtLBCaHbWbs4SqzBEzJls6KolmXK2ApR4C7IOLJe3EjLwpNwbnACCrto_hMFBPW7wkGMXTRdzHg34kzgS4k3XPc6txIdcefx9QV1yynAAgKTMGVj5zBhBDoJ649hjn0yyyIyyC4dS3IIULnMoohY8qgbpIwq_8S7IMZ-YGYanR9hfGRpF7K8-aEGBbsNo9P8NxHE16TwSJ4i00ws1SgRFlGbdPqtCxZzYUhmn059yqZiqWPZuVjdCZJSO7IdCikOP_g','2023-12-12 14:02:48','2023-12-12 15:02:48',_binary '{\"@class\":\"java.util.Collections$UnmodifiableMap\",\"metadata.token.claims\":{\"@class\":\"java.util.Collections$UnmodifiableMap\",\"sub\":\"winmanboo\",\"aud\":[\"java.util.Collections$SingletonList\",[\"default\"]],\"uid\":[\"java.lang.Long\",1],\"nbf\":[\"java.time.Instant\",1702389767.626449000],\"iss\":[\"java.net.URL\",\"http://192.168.10.2:9000\"],\"admin\":true,\"exp\":[\"java.time.Instant\",1702393367.626449000],\"iat\":[\"java.time.Instant\",1702389767.626449000],\"authorities\":[\"java.util.ImmutableCollections$ListN\",[]],\"tenant\":[\"java.lang.Long\",0],\"username\":\"winmanboo\"},\"metadata.token.invalidated\":false}','Bearer',NULL,NULL,NULL,NULL,NULL,_binary '7gXnHSh5BoqvUXREL5fMEuv-y6WW1UUB-HohbXp2rpRDqj1Vr-094TdE3Xc6Rlm0XXhHwqPbbOV8rmff_d8--odohh1nCpj-G7brRxTtDJ-ZtleJrih-2i9FI33USH2D','2023-12-12 14:02:48','2023-12-13 02:02:48',_binary '{\"@class\":\"java.util.Collections$UnmodifiableMap\",\"metadata.token.invalidated\":false}','4cf00193ca9a47fa8b27b7d2c24e145e'),('bbd705b9-986a-4197-918c-835c51e24949','jiaming-default-id','default','captcha',NULL,_binary '{\"@class\":\"java.util.Collections$UnmodifiableMap\"}',NULL,NULL,NULL,NULL,NULL,_binary 'eyJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJ3aW5tYW5ib28iLCJhdWQiOiJkZWZhdWx0IiwidWlkIjoxLCJuYmYiOjE3MDI0NjQ3MjUsImlzcyI6Imh0dHA6Ly8xOTIuMTY4LjEwLjI6OTAwMCIsImFkbWluIjp0cnVlLCJleHAiOjE3MDI0NjgzMjUsImlhdCI6MTcwMjQ2NDcyNSwiYXV0aG9yaXRpZXMiOltdLCJ0ZW5hbnQiOjAsInVzZXJuYW1lIjoid2lubWFuYm9vIn0.XEXrWvwSPJae5B_0YBS7w0jT3tdPOsz4jzsWwMqo2v80CWRAEZfyVOryoqzJ5djI_UiOKfJmyYoIQbhOrLccTj0oPRePP7VWYYcLBl1_K2XwHqqWWWelBh3bMya7K7WeU6fFckMp_l3jifoReFpuoDWk5fUkYWN-EzOHEEzBDvXx5XwNDcPd6OJPhTEdysjkHZrpv-y9jvWlLJgr7AEmr0HF4isqa6ab7rYuI5Q2Vilj_byvOf2gbvYSGpRC6Y26qYvC-vMhghrYaq5l5JBUYVC7CNuOHx4qqUhhTxMNPulDwkp19ETkMLH-08vQn9myaKLsEmcvyHnksTnCC4hRxQ','2023-12-13 10:52:05','2023-12-13 11:52:05',_binary '{\"@class\":\"java.util.Collections$UnmodifiableMap\",\"metadata.token.claims\":{\"@class\":\"java.util.Collections$UnmodifiableMap\",\"sub\":\"winmanboo\",\"aud\":[\"java.util.Collections$SingletonList\",[\"default\"]],\"uid\":[\"java.lang.Long\",1],\"nbf\":[\"java.time.Instant\",1702464725.114690000],\"iss\":[\"java.net.URL\",\"http://192.168.10.2:9000\"],\"admin\":true,\"exp\":[\"java.time.Instant\",1702468325.114690000],\"iat\":[\"java.time.Instant\",1702464725.114690000],\"authorities\":[\"java.util.ImmutableCollections$ListN\",[]],\"tenant\":[\"java.lang.Long\",0],\"username\":\"winmanboo\"},\"metadata.token.invalidated\":false}','Bearer',NULL,NULL,NULL,NULL,NULL,_binary '8XJHmrIiFkxu8VZGuFlrUm2W8dB_iupzJlNUazQPadf73Gy6-jiBHBe2-uTdyquSEketMigDzjaNlidYGPTsIWPZ_5_z6OH-Zq4u7n_BUzlCVj_wre4vQPEdebuduhtr','2023-12-13 10:52:05','2023-12-13 22:52:05',_binary '{\"@class\":\"java.util.Collections$UnmodifiableMap\",\"metadata.token.invalidated\":false}','26e4ffcd115045febd7c3cd7b8ce71d2'),('bdb80ef1-dbfb-4082-8659-3b7744768dbf','jiaming-default-id','default','captcha',NULL,_binary '{\"@class\":\"java.util.Collections$UnmodifiableMap\"}',NULL,NULL,NULL,NULL,NULL,_binary 'eyJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJ3aW5tYW5ib28iLCJhdWQiOiJkZWZhdWx0IiwidWlkIjoxLCJuYmYiOjE3MDMxNjc2NDIsImlzcyI6Imh0dHA6Ly8xOTIuMTY4LjEwLjI6OTAwMCIsImFkbWluIjp0cnVlLCJleHAiOjE3MDMxNzEyNDIsImlhdCI6MTcwMzE2NzY0MiwiYXV0aG9yaXRpZXMiOltdLCJ0ZW5hbnQiOjAsInVzZXJuYW1lIjoid2lubWFuYm9vIn0.ZljcEdvJqBkXdcGT84dci4SxYX9MUe2b9VS6dJToEFHgPjb_aucMeUI26zWDMUuf_HET1xYk-3JsSi1HIqUSrWLlrDFZHtJO0vgiNkEQ-q5L2P_XpqUaGzC4xZcmDF52l2AIaG3Bk97rhrtkBRcbxkXcEf43brkYZ2AGK_ucx1O2aSqLFMmhBnxGxgaJjnVs0eCyQYfe6hjEaQdk86_8DuKA6xGoYUhV_rb4YWoWWu0nvK0BR-J2EyhsnniOZcx_iVm_g1qIUB8Lu1ErjnIBfvmsGaQs9vX4J156e-4k4XIVKssPF0gg0cnv1wAsrWAUhk2oqpZN4wO_DHwt6akgcg','2023-12-21 14:07:22','2023-12-21 15:07:22',_binary '{\"@class\":\"java.util.Collections$UnmodifiableMap\",\"metadata.token.claims\":{\"@class\":\"java.util.Collections$UnmodifiableMap\",\"sub\":\"winmanboo\",\"aud\":[\"java.util.Collections$SingletonList\",[\"default\"]],\"uid\":[\"java.lang.Long\",1],\"nbf\":[\"java.time.Instant\",1703167642.168229000],\"iss\":[\"java.net.URL\",\"http://192.168.10.2:9000\"],\"admin\":true,\"exp\":[\"java.time.Instant\",1703171242.168229000],\"iat\":[\"java.time.Instant\",1703167642.168229000],\"authorities\":[\"java.util.ImmutableCollections$ListN\",[]],\"tenant\":[\"java.lang.Long\",0],\"username\":\"winmanboo\"},\"metadata.token.invalidated\":false}','Bearer',NULL,NULL,NULL,NULL,NULL,_binary 'eHeX1Dufscc8bW4au2xjjLAI0dluFkarm2LhV6PnE6FqWyMeRXt-SGkE02G_EPc9uZB02gX4wUF2CWlKKUDPYVQwtesL3fiJRtSFY-rQMp5g8cCaxreYnUbWAZQhQDcG','2023-12-21 14:07:22','2023-12-22 02:07:22',_binary '{\"@class\":\"java.util.Collections$UnmodifiableMap\",\"metadata.token.invalidated\":false}','b94e3884206442bea0349bbaa418c825'),('c65f72a7-47c6-4347-b1cd-d53f94af51b6','jiaming-default-id','default','captcha',NULL,_binary '{\"@class\":\"java.util.Collections$UnmodifiableMap\"}',NULL,NULL,NULL,NULL,NULL,_binary 'eyJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJ3aW5tYW5ib28iLCJhdWQiOiJkZWZhdWx0IiwidWlkIjoxLCJuYmYiOjE3MDMxNTc1MjYsImlzcyI6Imh0dHA6Ly8xOTIuMTY4LjEwLjI6OTAwMCIsImFkbWluIjp0cnVlLCJleHAiOjE3MDMxNjExMjYsImlhdCI6MTcwMzE1NzUyNiwiYXV0aG9yaXRpZXMiOltdLCJ0ZW5hbnQiOjAsInVzZXJuYW1lIjoid2lubWFuYm9vIn0.EsyKefIzREj_rW6AqB2EoxobARm0cfRBz6XjURkR_WOMgizYekH9_lgZ1ElsE5qFNLX_ZI3_w7WuAFNEftIsSkf_OcvrT-_O1AzW6FIYgahqt6iSOD3sJS5IbGN6qqfFj5RHv-2hDAMzZuaswZ51LWeoS3eMA5UpuDeao7WgJqNFbCHcCDkGm7DzWrfNwfH7FRyquTyOXttQIwk3aZy88h1VvJmmRjPxIrU42vld05eTu2tNJ_TpArN0ENGSS8NyI-wV8NCoSMNqy3ZWGVoazU_OsN-qJNabX7xy80FfhPESf1TemVOSQX9Ry2oS2u6884IKNUEP9blRQ1Oa_oJcGA','2023-12-21 11:18:47','2023-12-21 12:18:47',_binary '{\"@class\":\"java.util.Collections$UnmodifiableMap\",\"metadata.token.claims\":{\"@class\":\"java.util.Collections$UnmodifiableMap\",\"sub\":\"winmanboo\",\"aud\":[\"java.util.Collections$SingletonList\",[\"default\"]],\"uid\":[\"java.lang.Long\",1],\"nbf\":[\"java.time.Instant\",1703157526.535727000],\"iss\":[\"java.net.URL\",\"http://192.168.10.2:9000\"],\"admin\":true,\"exp\":[\"java.time.Instant\",1703161126.535727000],\"iat\":[\"java.time.Instant\",1703157526.535727000],\"authorities\":[\"java.util.ImmutableCollections$ListN\",[]],\"tenant\":[\"java.lang.Long\",0],\"username\":\"winmanboo\"},\"metadata.token.invalidated\":false}','Bearer',NULL,NULL,NULL,NULL,NULL,_binary 'uZe78eB2FygP8JBscy0O8kcGnC1ffmhTm8sNC6GAKHmwCI9Y4FPRtsG1rShE-2FRSe8671Q_OZ39eoJlSaTKNXWoRt02zuF_2NA87MN9Swhq52NF22JnHnzqbPbVgQh7','2023-12-21 11:18:47','2023-12-21 23:18:47',_binary '{\"@class\":\"java.util.Collections$UnmodifiableMap\",\"metadata.token.invalidated\":false}','fd1dc7befa7e4c56a6b7fbf2cd7311e2'),('fece14b3-051d-41bb-8965-68c35cad4d13','jiaming-default-id','default','captcha',NULL,_binary '{\"@class\":\"java.util.Collections$UnmodifiableMap\"}',NULL,NULL,NULL,NULL,NULL,_binary 'eyJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJ3aW5tYW5ib28iLCJhdWQiOiJkZWZhdWx0IiwidWlkIjoxLCJuYmYiOjE3MDI0NTQxMDIsImlzcyI6Imh0dHA6Ly8xOTIuMTY4LjEwLjI6OTAwMCIsImFkbWluIjp0cnVlLCJleHAiOjE3MDI0NTc3MDIsImlhdCI6MTcwMjQ1NDEwMiwiYXV0aG9yaXRpZXMiOltdLCJ0ZW5hbnQiOjAsInVzZXJuYW1lIjoid2lubWFuYm9vIn0.VQ1f6Oxqf32uqjYYqp1HZ1xZqtfD91k4kp7XiEQZMddWeZmJS0pJZAAbn0kwJcKvU8Y3IbxuUzXPKIAECOmkxZ_NosSSaNase68WqjOeafmA2EiNsOK155dpm0iyc2NMT5yCcS4r6m7n4d2x5BMJHxm-dKer_mUFRUftAjuOo67AJIB9zqk6HQaNmyWzZIEoFopIlRkIpmc2-kNKDpFukQCjZOiKzNzI2vXG0do9VTAHqAYDps6VFyQxKsEOlWU2Rx4KY7Y1XjY6set4k37XSleYvJIU6aqyLMLeLvos9F28Uo5MG28lHp_Ckdf5G--c3gcKku5nEWzIrl8yP_GEeA','2023-12-13 07:55:03','2023-12-13 08:55:03',_binary '{\"@class\":\"java.util.Collections$UnmodifiableMap\",\"metadata.token.claims\":{\"@class\":\"java.util.Collections$UnmodifiableMap\",\"sub\":\"winmanboo\",\"aud\":[\"java.util.Collections$SingletonList\",[\"default\"]],\"uid\":[\"java.lang.Long\",1],\"nbf\":[\"java.time.Instant\",1702454102.514828000],\"iss\":[\"java.net.URL\",\"http://192.168.10.2:9000\"],\"admin\":true,\"exp\":[\"java.time.Instant\",1702457702.514828000],\"iat\":[\"java.time.Instant\",1702454102.514828000],\"authorities\":[\"java.util.ImmutableCollections$ListN\",[]],\"tenant\":[\"java.lang.Long\",0],\"username\":\"winmanboo\"},\"metadata.token.invalidated\":false}','Bearer',NULL,NULL,NULL,NULL,NULL,_binary 'tPPBVj1tXbj732njT2EJbUdIRFFX6dU2Om31cXEdC129XsoIGJXJ29XpW2wPqrbKtGAFTZBLTMmaIMPutrnsEeeTo5gHv1L6b4vLr66T7dMnqGXSAFswrNOKswxepLk9','2023-12-13 07:55:03','2023-12-13 19:55:03',_binary '{\"@class\":\"java.util.Collections$UnmodifiableMap\",\"metadata.token.invalidated\":false}','2f12bc34888e4f4c8e557648ef2075b2');
/*!40000 ALTER TABLE `oauth2_authorization` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `oauth2_authorization_consent`
--

DROP TABLE IF EXISTS `oauth2_authorization_consent`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `oauth2_authorization_consent` (
  `registered_client_id` varchar(100) COLLATE utf8mb4_general_ci NOT NULL,
  `principal_name` varchar(200) COLLATE utf8mb4_general_ci NOT NULL,
  `authorities` varchar(1000) COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`registered_client_id`,`principal_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `oauth2_authorization_consent`
--

LOCK TABLES `oauth2_authorization_consent` WRITE;
/*!40000 ALTER TABLE `oauth2_authorization_consent` DISABLE KEYS */;
/*!40000 ALTER TABLE `oauth2_authorization_consent` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `oauth2_registered_client`
--

DROP TABLE IF EXISTS `oauth2_registered_client`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `oauth2_registered_client` (
  `id` varchar(100) COLLATE utf8mb4_general_ci NOT NULL,
  `client_id` varchar(100) COLLATE utf8mb4_general_ci NOT NULL,
  `client_id_issued_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `client_secret` varchar(200) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `client_secret_expires_at` timestamp NULL DEFAULT NULL,
  `client_name` varchar(200) COLLATE utf8mb4_general_ci NOT NULL,
  `client_authentication_methods` varchar(1000) COLLATE utf8mb4_general_ci NOT NULL,
  `authorization_grant_types` varchar(1000) COLLATE utf8mb4_general_ci NOT NULL,
  `redirect_uris` varchar(1000) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `scopes` varchar(1000) COLLATE utf8mb4_general_ci NOT NULL,
  `client_settings` varchar(2000) COLLATE utf8mb4_general_ci NOT NULL,
  `token_settings` varchar(2000) COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `oauth2_registered_client`
--

LOCK TABLES `oauth2_registered_client` WRITE;
/*!40000 ALTER TABLE `oauth2_registered_client` DISABLE KEYS */;
INSERT INTO `oauth2_registered_client` VALUES ('jiaming-default-id','default','2023-07-20 04:12:01','$2a$10$Yoi0zjP5honzQNFsWf4OrO0YrVfv5iC44tfIVS2MVQxGMgLrV/Fau',NULL,'jiaming-default-id','client_secret_basic','refresh_token,client_credentials,captcha,authorization_code,urn:ietf:params:oauth:grant-type:jwt-bearer','http://localhost:8080/jiaming/uaa/auth/code','all','{\"@class\":\"java.util.Collections$UnmodifiableMap\",\"settings.client.require-proof-key\":false,\"settings.client.require-authorization-consent\":true}','{\"@class\":\"java.util.Collections$UnmodifiableMap\",\"settings.token.reuse-refresh-tokens\":true,\"settings.token.id-token-signature-algorithm\":[\"org.springframework.security.oauth2.jose.jws.SignatureAlgorithm\",\"RS256\"],\"settings.token.access-token-time-to-live\":[\"java.time.Duration\",3600.000000000],\"settings.token.access-token-format\":{\"@class\":\"org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat\",\"value\":\"self-contained\"},\"settings.token.refresh-token-time-to-live\":[\"java.time.Duration\",43200.000000000],\"settings.token.authorization-code-time-to-live\":[\"java.time.Duration\",300.000000000]}');
/*!40000 ALTER TABLE `oauth2_registered_client` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_dept`
--

DROP TABLE IF EXISTS `sys_dept`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_dept` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '部门id',
  `name` varchar(30) COLLATE utf8mb4_general_ci NOT NULL COMMENT '部门名称',
  `parent_id` bigint NOT NULL COMMENT '父部门id',
  `leader_user_id` bigint DEFAULT NULL COMMENT '负责人',
  `phone` varchar(11) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '联系电话',
  `email` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '邮箱',
  `status` tinyint NOT NULL DEFAULT '1' COMMENT '部门状态 0:停用 1:正常',
  `creator` varchar(64) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建者',
  `updater` varchar(64) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '更新者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` tinyint NOT NULL DEFAULT '0' COMMENT '是否删除 0:未删除 1:删除',
  `tenant_id` bigint NOT NULL DEFAULT '0' COMMENT '租户id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='部门表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_dept`
--

LOCK TABLES `sys_dept` WRITE;
/*!40000 ALTER TABLE `sys_dept` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_dept` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_group_role`
--

DROP TABLE IF EXISTS `sys_group_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_group_role` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '用户组与角色关联id',
  `user_group_id` bigint NOT NULL COMMENT '用户组id',
  `role_id` bigint NOT NULL COMMENT '角色id',
  `creator` varchar(64) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建者',
  `updater` varchar(64) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '更新者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `tenant_id` bigint NOT NULL DEFAULT '0' COMMENT '租户id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='用户组与角色关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_group_role`
--

LOCK TABLES `sys_group_role` WRITE;
/*!40000 ALTER TABLE `sys_group_role` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_group_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_group_user`
--

DROP TABLE IF EXISTS `sys_group_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_group_user` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '用户与用户组关联id',
  `user_group_id` bigint NOT NULL COMMENT '用户组id',
  `user_id` bigint NOT NULL COMMENT '用户id',
  `creator` varchar(64) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建者',
  `updater` varchar(64) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '更新者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `tenant_id` bigint NOT NULL DEFAULT '0' COMMENT '租户id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='用户与用户组关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_group_user`
--

LOCK TABLES `sys_group_user` WRITE;
/*!40000 ALTER TABLE `sys_group_user` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_group_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_menu`
--

DROP TABLE IF EXISTS `sys_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_menu` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '菜单id',
  `name` varchar(32) COLLATE utf8mb4_general_ci NOT NULL COMMENT '菜单名称',
  `permission` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '权限标识',
  `parent_id` bigint unsigned NOT NULL DEFAULT '0' COMMENT '父菜单id',
  `path` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '路径',
  `component` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '1:Layout 不会跳转界面 2:demo/demo 会跳转到该界面',
  `redirect` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '当设置noRedirect时，该路由在面包屑导航中不能被点击',
  `always_show` tinyint DEFAULT NULL COMMENT '是否一直现实根路由',
  `hidden` tinyint DEFAULT NULL COMMENT '当设置未true时，该路由不会出现侧边栏，用于一些编辑界面',
  `title` varchar(32) COLLATE utf8mb4_general_ci NOT NULL COMMENT '设置路由在侧边栏和面包屑中展示的名字',
  `icon` varchar(32) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '设置该路由的图标，支持 svg-class，也支持 el-icon-x',
  `no_cache` tinyint DEFAULT NULL COMMENT '如果设置为true，则不会被缓存，默认false',
  `breadcrumb` tinyint DEFAULT NULL COMMENT '如果设置为false，则不会在面包屑中展示（默认true）',
  `affix` tinyint DEFAULT NULL COMMENT '如果设置为true，则会固定在 tags-view 中（默认false）',
  `active_menu` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '当路由设置该属性，则会高亮相对应的侧边栏',
  `creator` varchar(64) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建者',
  `updater` varchar(64) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '更新者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` tinyint NOT NULL DEFAULT '0' COMMENT '是否删除 0:未删除 1:删除',
  `enable` tinyint NOT NULL DEFAULT '1' COMMENT '是否启用 1：启用 0：不启用',
  `type` tinyint NOT NULL DEFAULT '0' COMMENT '菜单类型 0：目录 1：菜单 2：按钮',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='系统菜单';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_menu`
--

LOCK TABLES `sys_menu` WRITE;
/*!40000 ALTER TABLE `sys_menu` DISABLE KEYS */;
INSERT INTO `sys_menu` VALUES (1,'系统管理',NULL,0,'/sys','Layout',NULL,NULL,NULL,'系统管理','el-icon-office-building',NULL,NULL,NULL,NULL,NULL,NULL,'2023-06-08 16:52:43','2023-07-05 20:36:48',0,1,0),(2,'用户管理',NULL,1,'user','sys/user',NULL,NULL,NULL,'用户管理','el-icon-user-solid',NULL,NULL,NULL,NULL,NULL,NULL,'2023-06-08 16:54:19','2023-07-20 11:07:47',0,1,1),(3,'角色管理',NULL,1,'role','sys/role',NULL,NULL,NULL,'角色管理','el-icon-user',NULL,NULL,NULL,NULL,NULL,NULL,'2023-06-08 16:55:48','2023-07-20 11:07:47',0,1,1),(4,'菜单管理',NULL,1,'menu','sys/menu',NULL,NULL,NULL,'菜单管理',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2023-06-08 16:56:12','2023-07-20 11:07:47',0,1,1),(5,'部门管理',NULL,1,'dept','sys/dept',NULL,NULL,NULL,'部门管理',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2023-06-08 16:57:09','2023-07-20 11:07:47',0,1,1),(6,'岗位管理',NULL,1,'post','sys/post',NULL,NULL,NULL,'岗位管理',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2023-06-08 16:57:41','2023-07-20 11:07:47',0,1,1),(7,'OAuth2.1',NULL,1,'oauth2','ParentView',NULL,1,0,'OAuth2.1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2023-06-08 16:58:22','2023-07-20 12:25:49',0,1,1),(8,'租户管理',NULL,1,'tenant','sys/tenant',NULL,NULL,NULL,'租户管理',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2023-06-08 16:58:56','2023-07-20 11:07:47',0,1,1),(9,'应用管理',NULL,7,'app','sys/oauth2/app',NULL,0,0,'应用管理',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2023-07-20 13:13:22','2023-07-20 13:14:42',0,1,1),(10,'令牌管理',NULL,7,'token','sys/oauth2/token',NULL,0,0,'令牌管理',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2023-07-20 13:13:49','2023-07-20 13:14:42',0,1,1);
/*!40000 ALTER TABLE `sys_menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_post`
--

DROP TABLE IF EXISTS `sys_post`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_post` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '岗位id',
  `code` varchar(64) COLLATE utf8mb4_general_ci NOT NULL COMMENT '岗位标识',
  `name` varchar(50) COLLATE utf8mb4_general_ci NOT NULL COMMENT '岗位名称',
  `status` tinyint NOT NULL DEFAULT '1' COMMENT '状态 0:停用 1:正常',
  `remark` varchar(500) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  `creator` varchar(64) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建者',
  `updater` varchar(64) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '更新者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` tinyint NOT NULL DEFAULT '0' COMMENT '是否删除 0:未删除 1:删除',
  `tenant_id` bigint NOT NULL DEFAULT '0' COMMENT '租户id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='岗位表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_post`
--

LOCK TABLES `sys_post` WRITE;
/*!40000 ALTER TABLE `sys_post` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_post` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_role`
--

DROP TABLE IF EXISTS `sys_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_role` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '角色id',
  `name` varchar(30) COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色名称',
  `code` varchar(100) COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色权限标识',
  `status` tinyint NOT NULL COMMENT '角色状态 0:停用 1:启用',
  `remark` varchar(500) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  `creator` varchar(64) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建者',
  `updater` varchar(64) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '更新者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` tinyint NOT NULL DEFAULT '0' COMMENT '是否删除 0:未删除 1:删除',
  `tenant_id` bigint NOT NULL DEFAULT '0' COMMENT '租户编号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='系统角色';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role`
--

LOCK TABLES `sys_role` WRITE;
/*!40000 ALTER TABLE `sys_role` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_role_menu`
--

DROP TABLE IF EXISTS `sys_role_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_role_menu` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '角色菜单关联id',
  `role_id` bigint NOT NULL COMMENT '角色id',
  `menu_id` bigint NOT NULL COMMENT '菜单id',
  `creator` varchar(64) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建者',
  `updater` varchar(64) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '更新者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `tenant_id` bigint NOT NULL DEFAULT '0' COMMENT '租户id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='角色菜单关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role_menu`
--

LOCK TABLES `sys_role_menu` WRITE;
/*!40000 ALTER TABLE `sys_role_menu` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_role_menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_tenant`
--

DROP TABLE IF EXISTS `sys_tenant`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_tenant` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '租户id',
  `name` varchar(30) COLLATE utf8mb4_general_ci NOT NULL COMMENT '租户名',
  `user_id` bigint unsigned DEFAULT NULL COMMENT '用户id',
  `user_name` varchar(30) COLLATE utf8mb4_general_ci NOT NULL COMMENT '联系人',
  `mobile` varchar(30) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '联系方式',
  `status` tinyint NOT NULL DEFAULT '1' COMMENT '租户状态 0:停用 1:正常',
  `domain` varchar(256) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '绑定的域名',
  `package_id` bigint NOT NULL COMMENT '租户套餐id',
  `expire_time` datetime NOT NULL COMMENT '过期时间',
  `account_count` int NOT NULL COMMENT '账号配额',
  `creator` varchar(64) COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建者',
  `updater` varchar(64) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '更新者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` tinyint DEFAULT '0' COMMENT '是否删除 0:未删除 1:删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='租户表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_tenant`
--

LOCK TABLES `sys_tenant` WRITE;
/*!40000 ALTER TABLE `sys_tenant` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_tenant` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_tenant_package`
--

DROP TABLE IF EXISTS `sys_tenant_package`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_tenant_package` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '租户套餐id',
  `name` varchar(30) COLLATE utf8mb4_general_ci NOT NULL COMMENT '套餐名',
  `status` tinyint NOT NULL COMMENT '租户状态 0:停用 1:正常',
  `remark` varchar(256) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  `menu_ids` varchar(2048) COLLATE utf8mb4_general_ci NOT NULL COMMENT '关联的菜单编号',
  `creator` varchar(64) COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建者',
  `updater` varchar(64) COLLATE utf8mb4_general_ci NOT NULL COMMENT '更新者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` tinyint DEFAULT '0' COMMENT '是否删除 0:未删除 1:删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='租户套餐表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_tenant_package`
--

LOCK TABLES `sys_tenant_package` WRITE;
/*!40000 ALTER TABLE `sys_tenant_package` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_tenant_package` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user`
--

DROP TABLE IF EXISTS `sys_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_user` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '系统用户id',
  `username` varchar(30) COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户名',
  `password` varchar(100) COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码',
  `nickname` varchar(30) COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户昵称',
  `remark` varchar(500) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  `dept_id` bigint DEFAULT NULL COMMENT '部门id',
  `post_ids` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '岗位id数组，逗号分隔',
  `email` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '用户邮箱',
  `mobile` varchar(11) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '联系方式',
  `sex` tinyint DEFAULT NULL COMMENT '用户性别 0:女 1:男',
  `avatar` varchar(512) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '用户头像地址',
  `status` tinyint NOT NULL DEFAULT '1' COMMENT '账号状态 0:停用 1:正常',
  `login_ip` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '登录ip',
  `login_date` datetime DEFAULT NULL COMMENT '登录时间',
  `creator` varchar(64) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建者',
  `updater` varchar(64) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '更新者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` tinyint NOT NULL DEFAULT '0' COMMENT '是否删除 0:未删除 1:删除',
  `tenant_id` bigint NOT NULL DEFAULT '0' COMMENT '租户id，如果为0则为平台用户',
  `is_admin` tinyint NOT NULL DEFAULT '0' COMMENT '是否是管理员（0:不是 1:是）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='系统用户';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user`
--

LOCK TABLES `sys_user` WRITE;
/*!40000 ALTER TABLE `sys_user` DISABLE KEYS */;
INSERT INTO `sys_user` VALUES (1,'winmanboo','$2a$10$9BlR0UXxCl5jE5tYht13We3Qe9If8Vysd6OvTobsYYLWT4LhiqOgG','winmanboo',NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,NULL,NULL,NULL,NULL,'2023-06-01 21:31:54','2023-07-20 12:16:52',0,0,1);
/*!40000 ALTER TABLE `sys_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user_group`
--

DROP TABLE IF EXISTS `sys_user_group`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_user_group` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '用户组id',
  `name` varchar(255) COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户组名',
  `remark` varchar(512) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '描述',
  `status` tinyint NOT NULL DEFAULT '1' COMMENT '状态 0:停用 1:启用',
  `creator` varchar(64) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建者',
  `updater` varchar(64) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '更新者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` tinyint NOT NULL DEFAULT '0' COMMENT '是否删除 0:未删除 1:删除',
  `tenant_id` bigint NOT NULL DEFAULT '0' COMMENT '租户id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='用户组';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user_group`
--

LOCK TABLES `sys_user_group` WRITE;
/*!40000 ALTER TABLE `sys_user_group` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_user_group` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user_role`
--

DROP TABLE IF EXISTS `sys_user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_user_role` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '用户与角色关联id',
  `user_id` bigint NOT NULL COMMENT '用户id',
  `role_id` bigint NOT NULL COMMENT '角色id',
  `creator` varchar(64) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建者',
  `updater` varchar(64) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '更新者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` tinyint NOT NULL DEFAULT '0' COMMENT '是否删除 0:未删除 1:删除',
  `tenant_id` bigint unsigned NOT NULL DEFAULT '0' COMMENT '租户id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='用户与角色关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user_role`
--

LOCK TABLES `sys_user_role` WRITE;
/*!40000 ALTER TABLE `sys_user_role` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_user_role` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-12-22 18:06:14
