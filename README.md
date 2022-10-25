# Java Animator

###Demo
[![Demo Doccou alpha](https://i.imgur.com/1TMUGMW.gif)]
## Important Design Decisions

### SimpleAnimatorModel

Some key design decisions that were made to implement the `SimpleAnimatorModel` include:
- Using two maps, one of type `Map<String, IShape>` and another of type `Map<IShape,
  PriorityQueue<IAnimation>`. This way, each shape is stored uniquely using the name as its key.
  The animations for each shape can be accessed by using the `IShape` value from the first map
  as the key for the second map. The reasoning behind using a `PriorityQueue` over a list would
  be that a priority queue can be automatically sorted based on a `Comparator`. In this case,
  `Comparator.comparingInt` was used for the start times of the animations. This way, instead of
  sorting the list throughout the implementation, the animations would be sorted within the
  model itself.
- In the future, a new type of shape or animation might want to be supported. The way to do this
  is to simply add the type into the specified enum and extend the corresponding abstract class.
- When animations of the same type are added, there are various restrictions. Animations of the
  same type cannot have gaps, and the first animation of a designated type associated with a
  shape must start at the same time the shape is created. A gap is defined as the time between
  animations.
- The model does not allow for animations to have the same start and end time, which could
  be interpreted as teleporting animations. However, an animation could have the same values
  over a time, which indicates that there is no change. For example, a color change might change
  the color from the old color to the same color. This is still over a time interval, just the
  color doesn't change.
- The model does not allow for overlapping animations of the same type. For example, if a move
  was already occurring at time 1 to 10, there could not be another move from 3 to 7.
- The model uses Java's implementation of the `Color` class from `java.awt.Color`.
### View Implementation Changes

- Reconfigured the gap checker to add still movements, since there were gaps in the input files
- Added getters for RGB values to utilize for SVG view
- Added width and height for the model to accommodate for new views and constraints
- Added a tempo to the model
- Views account for real time and speed changes
- Added functionality for changing the state of the model at defined ticks using `interpolate()` 
  and `changeStateAtTick`. 
- Added the method `getFinalTime` to get the final tick of the animation to know when to stop 
  the timer.
- Added a makeshift controller that runs the visual view.

###Interactive View and Controller Implementation Changes

- Since the gap checker was not adding animations accordingly, it had to be changed. The issue 
  was that the gap checker wasn't actually adding the appropriate still animations between the 
  animations, it was only throwing an exception or only adding the still animation. To fix this, 
  when an animation is added, a still animation needs to be added between the end time of the 
  animation in the list already and the start time of the new animation. For example, to add two 
  animations, one from time 1 to 10 and one from time 20 to 30, the gap checker will add a third 
  animation not specified by the user from 10 to 20, in order to not have gaps. 
- Different classes were also abstracted in order to accommodate for future modification as well 
  as current implementation. For example, AbstractTextView and AbstractVisualController were 
  added in order to accommodate future implementations of these classes. 
- In the interactive view, buttons were added to it, including start, reset, etc. The button 
  functionality is specified below
  - Start: This button starts the animation.
  - Restart: This button will restore the animation to its original state before starting and 
    automatically run the animation. There is no need to press start again.
  - Pause: The pause button pauses the animation until resume is pressed. 
  - Resume: The resume button resumes an animation that is paused. 
  - Loop: This button will begin to loop the animation and also update the `Currently looping:` 
    text field indicator. Pressing it again will switch it off again.
  - Faster: Speeds up the animation.
  - Slower: Slows down the animation.
- Different new classes were made to create animations and export them to files.
- Changes made based on feedback from the previous assignment:
  - Added resources package to README
  - Removed debugging print outs to stdout
  - Removed the exception that was thrown for negative coordinate moves
  
## Interface and Class Design

### Animation

---
An overview for the design of the animations for the easy animator is shown below.
<img align="none" width="527" height="262" src="https://i.imgur.com/gI2XAjl.png">

- `IAnimation` : The client facing interface for animations. This interface includes public
  methods that allow the model and client to interact with the animations without directly
  accessing any of the fields.
- `AbstractAnimation` : To prepare for future implementation, an abstract class representation of
  animations provides extendable functionality to implement more types of animations. It includes
  protected fields that are only accessible by itself and the subclasses. It also includes
  appropriate getters to obtain fields from outside the class.
- `ColorChange` : The animation that changes the color of the shape. It has an additional field
  of the new color that will the shape will be changing to.
- `Resize` : The animation that changes the size of the shape. It has additional fields that
  consist of the final dimensions of the shape after it has been resized.
- `Move` : The animation that changes the position of the shape. It has additional fields that
  consist of the final coordinates of the shape after it has been moved.
- `Animation` : An enum that declares types of animations as their own types. To extend more
  types to the public enum, simply add a new type and then extend from the `AbstractAnimation`
  class.

### Shape

---
An overview for the design of the shapes for the easy animator is shown below. <br>
<img align="none" width="377" height="241" src="https://i.imgur.com/sIp4JKB.png">

- `IShape` : The client facing interface for shapes. This interface includes public
  methods that allow the model and client to interact with the shapes without directly
  accessing any of the fields.
- `AbstractShape` : To prepare for future implementation, an abstract class representation of
  shapes provides extendable functionality to implement more types of shapes. It includes
  protected fields that are only accessible by itself and the subclasses. It also includes
  appropriate getters to obtain fields from outside the class.
- `Rectangle` : The shape that represents a rectangle. It has fields consisting of the length
  and width, spawn and end times, as well as other fields.
- `Oval` : The shape that represents an oval. It has fields consisting of the height and width,
  spawn and end times, as well as other fields.
- `Shape` : An enum that declares types of shapes as their own types. To extend more
  types to the public enum, simply add a new type and then extend from the `AbstractShape`
  class.
>Note: In this implementation, there were no differentiating fields between rectangle and oval.
> However, the abstraction is useful for implementing shapes in the future, like a circle for
> example. A circle would not have two dimensions, it would just have a radius.

### Model


---
An overview for the design of the model for the easy animator is shown below. <br>
<img align="none" width="257" height="244" src="https://i.imgur.com/sTHXBoL.png">

- `AnimatorModelState` : The client facing interface for the model. This interface includes public
  methods that allow the client to interact with the model. It allows the access to various
  aspects of the state of the model. It does not provide any operations to mutate the state of
  an animator model.
- `AnimatorModel` : This is the main interface of the animator. It provides an interface
  for the animator model, which includes a multitude of functionalities where the model can
  interact with shapes and animations. This model only uses interfaces and abstractions.
- `SimpleAnimatorModel` : This is the main implementation of the animator. It provides the
  implementation of the interface `AnimatorModel`.

### View

---
An overview for the design of the view is shown below. <br>
<img align="none" width="626" height="233" src="https://i.imgur.com/hZcFD5H.png">
The old design is shown below. <br>
<img align="none" width="228" height="145" src="https://i.imgur.com/Tn2P8hs.png">

### MultiView Implementation
- `AnimatorView` : The client facing interface for the view. This interface includes public
  methods that allow the client to interact with the view. It is able to be converted into a
  string to later be used for further applications, such as user interfaces or a controller. The
  view is mainly used to display model state to the user.
- `AbstractTextView` : To prepare for future implementation, an abstract class representation of
  text based views provides extendable functionality to implement more types of text based views.
  It includes protected fields that are only accessible by itself and the subclasses. It also 
includes appropriate getters to obtain fields from outside the class.
- `AnimatorTextView` : The text view implementation for the animator. It extends the 
  `AbstractTextVew` class.
- `AnimatorSVGView` : The SVG view implementation for the animator. It extends the
  `AbstractTextVew` class.
- `AnimatorViewVisual` : The client facing interface for visual based views that extends 
  `AnimatorView`. 
  This 
  interface includes public
  methods that allow the client to interact with visual views. This view is to represent visual 
  animations created with file inputs. 
- `VisualView` : The class that implements visual views. An `AbstractVisualView` could be 
  created in the future to further expand the implementation for visual views, but in the 
  context of this assignment, there was only one, so it was not implemented. 
- `ViewType` : An enum was added to support the view types for more expandable implementation. 
### Previous Text View Implementation
- `AnimatorView` : The client facing interface for the view. This interface includes public
  methods that allow the client to interact with the view. It is able to be converted into a
  string to later be used for further applications, such as user interfaces or a controller. The
  view is mainly used to display model state to the user.
- `AnimatorTextView` : The implementation for the `AnimatorView` interface. It includes a
  `toString` method which converts the model state to a legible output string, like below.
- At its core, the way the view works is various `toString` methods from shapes, animations, as
  well as the model itself.
```
Shape r RECTANGLE Spawns at:1 Ends at:20
Created with  X:0.0 Y:0.0 Color:[0, 0, 0].  Width:10.0 Height:20.0

MOVE move1 Start:1 X:0.0 Y:0.0 Ends:15 X:6.0 Y:6.0
RESIZE resize1 Start:1 Width:10.0 Height:20.0 Ends:15 Width:6.0 Height:6.0
COLORCHANGE colorchange1 Start:1 Color:[0, 0, 0] Ends: 15 Color:[255, 0, 0]
COLORCHANGE colorchange2 Start:15 Color:[255, 0, 0] Ends: 20 Color:[0, 0, 255]
MOVE move2 Start:15 X:6.0 Y:6.0 Ends:20 X:6.0 Y:6.0
RESIZE resize2 Start:15 Width:6.0 Height:6.0 Ends:20 Width:10.0 Height:10.0

Shape o OVAL Spawns at:2 Ends at:20
Created with  X:5.0 Y:5.0 Color:[255, 255, 255].  Width:5.0 Height:5.0

MOVE move3 Start:2 X:5.0 Y:5.0 Ends:15 X:6.0 Y:6.0
RESIZE resize3 Start:2 Width:5.0 Height:5.0 Ends:15 Width:6.0 Height:6.0
COLORCHANGE colorchange3 Start:2 Color:[255, 255, 255] Ends: 15 Color:[255, 0, 0]
COLORCHANGE colorchange4 Start:15 Color:[255, 0, 0] Ends: 20 Color:[0, 0, 255]
MOVE move4 Start:15 X:6.0 Y:6.0 Ends:20 X:10.0 Y:10.0
RESIZE resize4 Start:15 Width:6.0 Height:6.0 Ends:20 Width:6.0 Height:6.0
```

### Controller
A simple de facto controller was created in order to initialize the timer as well as handle the 
`ActionListener`. It includes
- `IController` - The client facing interface for the controller. This interface includes public
  methods that allow the client to interact with the controller. The controller is run with 
  `animate()` that allows the client to run the views and the model.
- `Controller` - The implementation of the interface `IController` which extends 
  `AbstractVisualController` which represents a controller that creates a visual view. 
- `AbstractVisualController` - The abstract class for visual controllers.
- `InteractiveVisualViewController` - The implementation of the interface `IController` which 
  extends `IController` which represents a controller that creates an interactive view.

### IO
The IO package consists of
- `TweenModelBuilder` : The interface contains all the methods that the AnimationFileReader class
calls as it reads a file containing the animation and builds a model It is
parameterized over the model class `SimpleAnimatorModel`.
- `AnimationFileReader` : A class that reads input files to pass into the builder.
- `AnimationBuilder` : The implementation of the `TweenModelBuilder`.

### PreProgrammedAnimations

This package consists of two pre-built animations and the code that creates them. Both are 
visual representations of algorithms. Both animations create by default 12 pseudorandom rectangles 
of varying heights and positions, and sorts them by their specific sorting algorithm. In this case,
one can see the efficiency of the SelectionSort algorithm over the BubbleSort algorithm. These 
animations can be customized, but as they were not meant to be, this has limited functionality.
Both text files of the animations are in the resources package.
 - `AbstractPreProgrammedAnimation` : An abstract class that holds similar functions between both animations,
including inputs for the output file name and the number of rectangles to be had in the 
animation. Defaults to the tile of the algorithm as a text file, and 12 rectangles.
 - `BubbleSortPreProgrammedAnimation` : Sorts the array of rectangles according to a bubble sort algorithm.
This algorithm repeatedly swaps adjacent elements if they are in the wrong order. As it goes 
through the list several times, it is quite inefficient.
 - `SelectionSortPreProgrammedAnimation` : Sorts the array of rectangles according to a Selection Sort algorithm.
This algorithm finds the smallest element in the array and swaps it with the first element in the
array. It is far faster than BubbleSort, but is much slower on larger lists.

