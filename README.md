# Check out code
git clone git@github.com:aliciatang/CalendarSync.git
# Install bower dep
cd src/main/webapp/
bower install
# Lanch with mvn appengine
cd ../../..
mvn appengine:devserver
# Authorize the app
localhost:8080/auth
# Go the app
localhost:8080/index.html

