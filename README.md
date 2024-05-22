# Portfolio project IDATT2003 - 2024

GROUP ID = "0019"

STUDENT NAME = "Vetle Solheim Hodne"
<br>
STUDENT ID = "10013"


STUDENT NAME = "Edvard Berdal Eek"
<br>
STUDENT ID = "10036"


## Project description

This project revolves around visual representation of fractals. 
The chaos game is a method for creating fractals by randomly placing points 
in a plane and then moving a point towards the randomly placed points.
With affine transformations, the chaos game can be used to create fractals such as the Sierpinski triangle and the Barnsley fern.
Here the points are applied a random transformation from a set of transformations.

Chaos Julia-transformations are can be created by iterating a complex through a function, which takes the square root of
the sum of the complex number and another a constant complex number then randomly selects the
sign to be positive or negative, and then plotting the result.
 
## Project structure


- `IDATT2003-2024-0019-ChaosGame`
    - `src/` (folder for source files)
        - `main/`
            - `java/`
                - `edu.ntnu.stud/`
                    - `controller` (package for controller classes)
                        - `interfaces` (package for interfaces in controller package)
                            - `GameController.java` (interface for game controllers)
                            - `Observer.java` (interface for observer implementation)
                            - `Subject.java` (interface for subject implementation)
                        - `ChaosGameController.java` (controller for chaos game)
                        - `ChaosGameDescriptionFactory.java` (factory pattern for chaos game descriptions)
                        - `ChaosGameFileHandler.java` (file handler for chaos games)
                        - `ExploreGameController.java` (controller for explore game)
                        - `HomeController.java` (controller for home page)
                        - `PageController.java` (controller for pages)
                    - `model` (package for running the program)
                      - `chaos` (package for fractal game related entity classes)
                        - `ChaosCanvas.java` (class for chaos canvas)
                        - `ChaosGame.java` (class for chaos game)
                        - `ChaosGameDescription.java` (class for chaos game description)
                        - `ChaosGameType.java` (enum for chaos game type)
                        - `ExploreGame.java` (class for explore game)
                      - `linalg` (package for linear algebra related classes)
                        - `Complex.java` (class for complex numbers)
                        - `Matrix2x2.java` (class for 2x2 matrices)
                        - `Vector2D.java` (class for 2D vectors)
                      - `transformations` (package for transformations related classes)
                        - `AffineTransform2D.java` (class for affine transformations)
                        - `ExploreJulia.java` (class for explore julia)
                        - `JuliaTransform.java` (class for julia transformations)
                        - `Trandsform2D.java` (interface for 2D transformations)
                    - `view` (package for view classes)
                        - `ChaosPage.java` (view class for chaos game)
                        - `ExplorePage.java` (view class for explore game)
                        - `GamePage.java` (abstract class for game pages)
                        - `HomePage.java` (view class for the home page)
                - `module-info.java` (module info file)
            - `resources/` (folder for resources)
                - `media/` (folder for css files)
                  - `ChaosVideoFinal.mp4` (image for chaos game)
                  - `ExploreVideoFinal.mp4` (image for explore game)
                  - `White-home-icon.png` (image for home page)
                - `stylesheets/` (folder for fxml files)
                  - `style.css` (css file for styling the application)
                - `testFiles/` (folder for images)
                  - `barnsley.txt` (text file for barnsley fern)
                  - `juila.txt` (text file for julia set)
                  - `sierpinski.txt` (text file for sierpinski triangle)
                    
        - `test/` (folder for JUnit-tests)
            - `java/`
                - `edu.ntnu.stud/`
                    - `controller` (package for controller classes)
                        - `interfaces` (package for interfaces in controller package)
                            - `GameControllerTest.java` (interface for game controllers)
                            - `ObserverTest.java` (interface for observer implementation)
                            - `SubjectTest.java` (interface for subject implementation)
                        - `ChaosGameControllerTest.java` (controller for chaos game)
                        - `ChaosGameDescriptionFactoryTest.java` (factory pattern for chaos game descriptions)
                        - `ChaosGameFileHandlerTest.java` (file handler for chaos games)
                        - `ExploreGameControllerTest.java` (controller for explore game)
                        - `HomeControllerTest.java` (controller for home page)
                        - `PageControllerTest.java` (controller for pages)
                    - `model` (package for running the program)
                        - `chaos` (package for fractal game related entity classes)
                            - `ChaosCanvasTest.java` (class for chaos canvas)
                            - `ChaosGameTest.java` (class for chaos game)
                            - `ChaosGameDescriptionTest.java` (class for chaos game description)
                            - `ChaosGameTypeTest.java` (enum for chaos game type)
                            - `ExploreGameTest.java` (class for explore game)
                        - `linalg` (package for linear algebra related classes)
                            - `ComplexTest.java` (class for complex numbers)
                            - `Matrix2x2Test.java` (class for 2x2 matrices)
                            - `Vector2DTest.java` (class for 2D vectors)
                        - `transformations` (package for transformations related classes)
                            - `AffineTransform2DTest.java` (class for affine transformations)
                            - `ExploreJuliaTest.java` (class for explore julia)
                            - `JuliaTransformTest.java` (class for julia transformations)
## Link to repository
[//]: # (TODO: Include a link to your repository here.)
> https://gitlab.stud.idi.ntnu.no/idatt2003-19/chaosgame


## How to run the project

[//]: # (TODO: Describe how to run your project here. What is the main class? What is the main method?
What is the input and output of the program? What is the expected behaviour of the program?)

To run the program, run the main method in the MainApp class in the IDE of your choise.

Or use the following command your terminal:

```
mvn javafx:run
```
The program runs from the Main-class.
<p>Go to ChaosGame -> src -> main -> java -> edu.ntnu.stud -> Main and press run.

This runs the main method in the MainApp class which then runs the launch method for the application.

The user is presented with a home page that is split in the middle, one for the chaos game and one for the explore game.
when the user hovers over the chaos game, a video of the chaos game is displayed. 
When the user hovers over the explore game, a video of the explore game is displayed.
The user can click on the chaos game to play the chaos game, or click on the explore game to play the explore game.

In the chaos game the user can choose between displaying julia sets or affine fractals.
The user types in the amount of iterations the game should attempt to place a pixel.
They can also create their own fractals
The user can choose between displaying julia sets or affine fractals with a so-called chaos game.

In the explore game the user can explore the julia set by sliding the real and imaginary part sliders
changing the parts of the complex number which is used to display the fractal.
The zoom is used by scrolling and works both with and without a mouse.
In addition, there buttons to zoom in and out,
reset the image and a color picker allowing the user to change the color of the fractal

For each page the user can choose to go back to the home page by clicking the home icon in the top left corner.

On the home page there is a exit button in the bottom center of the application

## How to run the tests

[//]: # (TODO: Describe how to run the tests here.)
Tests can be run by typing the following in the terminal:

```
mvn test
```
or

```
mvn clean test
```

<br>
With IntelliJ IDEA, the tests can be run by right-clicking the edu.ntnu.stud package, either within the test or main folder,
and selecting "Run 'Tests in edu.ntnu.stud'". If you want to run tests with coverage, 
right-click the edu.ntnu.stud package and select "More Run/Debug" and then "Run 'Tests in edu.ntnu.stud' with Coverage".
The same can be done specific classes within the package.
## References


## References
[//]: # (TODO: Include references here, if any. For example, if you have used code from the course book, include a reference to the chapter.
Or if you have used code from a website or other source, include a link to the source.)

- [Chaos Game (wikipedia)](https://en.wikipedia.org/wiki/Chaos_game)
- [Julia Set (wikipedia)](https://en.wikipedia.org/wiki/Julia_set)
