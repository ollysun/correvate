Correvate Test

Follow this process carefully

Clone the project aand type this maben command: **mvn clean package**

This will generate the jar file inside the target folder

Build the dockerfile with this command

__docker build --tag=correvate:latest .__

Check the image using: docker image ls

Run the project with this command. 

__docker run -p8090:8080 correvate:latest__

Then you can test the project on postman or any other tools.

To upload multiple files on form data on POST method: use this link  http://localhost:8090/upload

To download the zip file, use the browser http://localhost:8090/downloadzip
