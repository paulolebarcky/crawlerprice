Code example for Evan Korth's Intro to Computer Science course.

Interacting with the Twitter API in Java
=================================================

1. Define a new Twitter application
-----------------------------------
   * Go to http://dev.twitter.com and sign in with your Twitter account
   * Go to <code>My applications</code> and create a new application (put any URL you want in the <code>Website</code> field)
   * After your application is created, go to the <code>Settings</code> tab and ensure the access is set to <code>Read and Write</code>

2. Create a new Maven project
-----------------------------
   * <b>NetBeans</b>
      * Go to <code>File</code> &gt; <code>New Project</code>, then select <code>Maven</code> and then <code>Java Application</code>
   * <b>Eclipse</b>
      * Go to <code>Help</code> &gt; <code>Install New Software</code>, click <code>Add</code>, then enter the following:
          * Name: <code>Maven (m2e)</code>
          * Location: <code>http://download.eclipse.org/technology/m2e/releases</code>
      * Make sure Maven is selected in the <code>Work with</code> field, then check the box next to <code>Maven Integration for Eclipse</code>, then click <code>Next</code> and follow the prompts to install Maven (this may take some time)
      * After Maven integration is installed, go to <code>File</code> &gt; <code>New</code> &gt; <code>Project</code>, then select <code>Maven</code> and then <code>Maven Project</code>

3. Add the <code>twitter4j</code> dependency
--------------------------------------------
   * <b>NetBeans</b>
      * Open <code>pom.xml</code> in the <code>Project Files</code> folder and insert the following XML between the <code>&lt;dependencies&gt;&lt;/dependencies&gt;</code> tags:
        <pre>
          &lt;dependency&gt;
              &lt;groupId&gt;org.twitter4j&lt;/groupId&gt;
              &lt;artifactId&gt;twitter4j-core&lt;/artifactId&gt;
              &lt;version&gt;[2.2,)&lt;/version&gt;
          &lt;/dependency&gt;
        </pre>
      * Select <code>Run</code> &gt; <code>Clean and Build Project</code>
   * <b>Eclipse</b>
      * Open <code>pom.xml</code> in the project root, then click the <code>Dependencies</code> tab at the bottom, then click <code>Add</code> next to the dependencies list on the left
      * Enter the following information, then click <code>OK</code>:
         * Group Id: <code>org.twitter4j</code>
         * Artifact Id: <code>twitter4j-core</code>
         * Version: <code>[2.2,)</code>

4. Create the <code>twitter4j.properties</code> configuration file
------------------------------------------------------------------
   * Go to your project folder in your computer's file system (do not do this from within NetBeans or Eclipse)
   * Create a plain text file called <code>twitter4j.properties</code> and insert the following (these values can be found in your application details on the Twitter developer site, http://dev.twitter.com):
     <pre>
      oauth.consumerKey=[your app's consumer key]
      oauth.consumerSecret=[your app's consumer secret]
      oauth.accessToken=[your access token]
      oauth.accessTokenSecret=[your access secret]
     </pre>
   * NOTE: You need to manually generate an access token and secret on the Twitter developer site. This access token is valid for your own Twitter account. If you want to get an access token on the fly (to authorize other Twitter accounts), you need to remove or comment out the <code>oauth.accessToken</code> and <code>oauth.accessTokenSecret</code> lines in the <code>twitter4j.properties</code> file, and then use the <code>twitter4j.auth</code> package in your code. See the <code>App.java</code> example code posted here for information on how to do this.

5. Play!
--------
   * Use the example code posted here as a guide to create a basic Twitter app.
   * Read the documentation to find out what else you can do: http://twitter4j.org/en/javadoc/twitter4j/package-summary.html
