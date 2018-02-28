# HOW TO RUN LOCALLY:
```
cd <autofill_trunk>// Because I use relative path to look up autofill.js and mock backend data.
jstf casperjs test [your test file] or mint test-js
```
## For gethering code coverage:
1. Environment configuration
    * install npm
    
        ```
        sudo yum install nodejs
        ```

    * update to latest version
    
        ```
        sudo npm install npm -g
        sudo npm cache clean -f
        sudo npm install -g n
        sudo n stable
        ```
        
    * Install istanbul
        ```
        npm install --save istanbul
        ```
2. Get coverate
    ```
    <autofill_trunk>/./get_js_coverage.sh
    ```
    or
    ```
    mint test-js-coverage
    ```
