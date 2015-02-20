# Watermarker

##### Used technologies:
 * Tomcat 7
 * gradle 1.8

To use watermarker in eclipse run 'gradle eclipse'
To generade Watermarker.war   run 'gradle war'


##### URLs to test the service

sending documents to service for watermarking (localhost and 8080 may differ):

http://<span></span>localhost:8080/Watermarker/rest/Watermarker/watermark?document={"title":"The Dark Code", "author":"Bruce Wayne", "topic":"Science"}  
http://<span></span>localhost:8080/Watermarker/rest/Watermarker/watermark?document={"title":"How to make money", "author":"Dr. Evil", "topic":"Business"}  
http://<span></span>localhost:8080/Watermarker/rest/Watermarker/watermark?document={"title":"Journal of human flight routes", "author":"Clark Kent"}  

polling status from service

http://<span></span>localhost:8080/Watermarker/rest/Watermarker/ticketStatus/1  
http://<span></span>localhost:8080/Watermarker/rest/Watermarker/ticketStatus/2  
http://<span></span>localhost:8080/Watermarker/rest/Watermarker/ticketStatus/3  

retriev document from service

http://<span></span>localhost:8080/Watermarker/rest/Watermarker/retrieve/1  
http://<span></span>localhost:8080/Watermarker/rest/Watermarker/retrieve/2  
http://<span></span>localhost:8080/Watermarker/rest/Watermarker/retrieve/3  
