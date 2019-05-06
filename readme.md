<!DOCTYPE html>

<html>
  <head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1">

  <title>CS 349 A2 (F18)</title>
  <meta name="description" content="CS 349 User Interfaces Winter 2019 at The University of Waterloo">

  <!-- <link href='https://fonts.googleapis.com/css?family=Ubuntu' rel='stylesheet' type='text/css'> -->
  <link href="https://fonts.googleapis.com/css?family=Open+Sans:400,400i,700|Source+Code+Pro" rel="stylesheet"/>
  <link rel="stylesheet" href="/~cs349/w19/css/main.css">
  <link rel="canonical" href="/~cs349/w19/assignments/a1.html">
  <link rel="alternate" type="application/rss+xml" title="CS 349 W19" href="/~cs349/w19/feed.xml">
</head>

<header>
    <nav role="navigation" class="navbar navbar-default navbar-fixed-top">
      <div class="container-fluid">
        <div class="navbar-header">
          <button data-toggle="collapse" data-target="#navbar-top-collapse-1" class="navbar-toggle">
          <span class="sr-only">Toggle navigation</span>
          <span class="icon-bar"></span>
          <span class="icon-bar"></span>
          <span class="icon-bar"></span></button>
          <a href="index.html" class="navbar-brand">CS349 W19</a></div>
          <div id="navbar-top-collapse-1" class="collapse navbar-collapse">
            <ul class="nav navbar-nav navbar-right">
                <li><a href="../index.html">Information</a></li>
                <li><a href="../schedule.html">Schedule</a></li>
                <li><a href="../resources.html">Resources</a></li>
              </ul>
        </div>
      </div>
    </nav>
  </header>

  <main class="container">
    <h1 id="a2">A2: Widgets and Event Handling (Java) -- JSketch</h1>
    <blockquote>
      <p>Due Fri Feb 8 at 5:00 PM</p>
    </blockquote>

    <h2 id="synopsis">Synopsis</h2>
    <p>In this assignment you will implement a vector-drawing program, consisting of a tool palettes and a canvas. Your program will allow a user to select drawing properties, and draw shapes on-screen.
    </p>

    <div>
        This is a rough mockup that illustrates the features that you will implement, and the basic layout that you must use. <br>
        <img src="a2mockup.png" alt="Mockup" align="center" width="400">
    </div> 

    <h2 id="gameplay">Requirements</h2>

    <p>The main area of your drawing program is a <strong>canvas</strong> that supports drawing shapes. Users can select a shape from the list on the side, specify color and border thickness, then click and drag the mouse on the canvas to draw that shape. A shape "preview" is shown as the mouse is dragged, and the shape is complete when the mouse button is released (i.e. the first click sets the starting position, and the drag operation sets the width of the circle, or creates a line, depending on the shape being drawn, and the shape is complete when the mouse button is released).</p>

    <p>Your drawing program will have the following layout and features:</p>
    <ul>
        <li>A <strong>menu bar</strong> with a File menu containing the following items: 
            <ul>
                <li><strong>New</strong>: create a new drawing. The drawing will always fit the canvas when it is created.</li>
                <li><strong>Load</strong>: load a drawing from a file (i.e. a file that you previously saved, which replaces the current drawing).</li>
                <li><strong>Save</strong>: save the current drawing (using a file format that you determine).</li>
            </ul>
        </li>

        <li>A <strong>tool palette</strong> on the left-hand side, supporting the following tools:
            <ul>
                <li><strong>A selection tool</strong>: this allows the user to select a shape that has been drawn (there should be some visual indication which shape is selected). To select a shape, the user should click this tool, then click on an existing shape. Pressing ESC on the keyboard will clear shape selection. Selecting a shape will cause the color palette and line thickness palette to update their state to reflect the border colour and line thickness of that shape (e.g. if I select a circle with the largest line thickness and a red border, the color palette should change to red, and the line thickness should change to the largest line to match the selected shape). Changing color or line thickness when a shape is selected will update the border colour and thickness to the new values. </li>
                <li><strong>An erase tool</strong>: click on this tool and then click on a shape to delete it.</li>
                <li><strong>A line drawing tool</strong>: select this to draw a line (using the selected color and the line thickness).</li>
                <li><strong>A circle drawing tool</strong>: select this to draw an unfilled circle at the point indicated (using the selected line thickness and color for the border).</li>
                <li><strong>A rectangle tool</strong>: select this to draw an unfilled rectangle (using the selected line thickness and color for the border).</li>
                <li><strong>A fill tool</strong>: select this tool, and click on a shape to fill that shape with the currently selected color. Note that rectangles and circles have a fill color, but lines should not be fillable (i.e. this tool cannot be applied to a line).</li>
            </ul>
        </li>

        <li>A <strong>color palette</strong>: a graphical display of at least six colors, which the user can use to select the current color. The current drawing color will be used for any new shapes that are drawn. If a shape is already selected when a color is chosen, the shape's border will change to that new color, in the case of a circle or rectangle. In the case of a line, this will change the line colour (since lines don't have an interior fill colour). Also, there should be a Chooser button that lets the user select an arbitrary color from a color picker dialog.</li>

        <li>A <strong>line thickness palette</strong>: a graphical display of at least three line widths that the user can select. Selecting a line width will set the border thickness for any new shapes drawn. Selecting a shape will change the border thickness to reflect the line thickness of that shape.</li>

    </ul>

    <p>The following functionality should be supported:</p>
    <ul>
        <li>The user should be able to move a selected shape around the screen by selecting it, and dragging it with the mouse. You should determine rules for how overlapping shapes are handled (the assumption is that shapes will overlap, but you need to determine exactly how to handle this).</li>
        <li>Your interface should clearly indicate which tool, color and line thickness are selected at any time. It should also be obvious which shape, if any, is selected. You may use thicker borders around the tool (as in the example above), or some other visual indicator that you choose.</li>
        <li>Your interface should enable/disable controls if appropriate (e.g. if an option is invalid, you should disable the control). </li>
        <li>Your application should run at a fixed size (defined below) and not be resizable. You should actively prevent window resizing.</li>
        <!-- <li>Your application should support dynamic resizing of the application window, so that the application adapts to different window sizes and resolutions. The tool palettes should expand and contract based upon available space; how the layout changes is a visual design decision left to you. The canvas should <em>not</em> change size, but should display scrollbars if the user resizes the window smaller than the canvas (otherwise the scrollbars should remain hidden). You may set a "reasonable" minimum size for your application window (e.g. 640x480), but there should be no limit to the maximum size.</li> -->
    </ul>
</p>

<h2>Additional Features (Mandatory, choose one or more features from the list below, totalling 10%).</h2>

<ul>
    <li>Scale shapes: the ability to change the scale/size of any shape by selecting it, then grabbing a corner of the shape, and dragging to increase/decrease it's size (5 marks).</li>
    <li>Group shapes: select multiple shapes, then group them together so that they can be dragged/resized as a single entity (10 marks).</li>
    <li>Customizable color palette: you can wholly or partially customize color buttons in the palette (e.g. right-click a button and choose a new color for that button from a color-chooser dialog) (5 marks).</li>
    <!-- <li>Dynamic use of widgets: if display is resized too small, replace a set of widgets with a space-optimized widget (e.g. replace stroke width with combo box, color palette with subset of buttons and so on). This should be a dynamic manipulation of at least 2 sets of widgets (10 marks).</li> -->
    <!-- <li>Undockable toolbars: toolbars can be undocked to create floating palettes, that can be repositioned around the screen (10 marks).</li> -->
    <li>System-level copy/paste: support pasting the entire canvas an an image (using any supported format) to an outside program (e.g. MS Paint, Photoshop, Pixelmator) (10 marks).</li>
</ul>

<h2>Technical Requirements</h2>

<p>The following is a list of technical constraints and guidelines:</p>
<ul>
    <li>The assignment must be programmed in Java using Swing components.</li>
    <li>The starting application window should be no larger than 1600x1200.</li>
    <!-- <li>The layout should be dynamic and designed appropriately to support scaling the application, as described above.</li> -->
    <li>Appropriate widgets should be selected and used for the interface. Options are appropriately enabled/disabled in the UI.</li>
    <li>You may use third-party graphics/images, provided that (a) they are licensed to allow you to use them in this way (e.g. Creative Commons), and (b) you comment their origin and license in your README file.</li>
    <li>You may use any sample code provided in-class (including on the class repository). If you do this, you <strong>must</strong> provide a comment in the code indicating the source of the code. If you use and fail to disclose this information, it could be considered an academic violation. </li>
</ul>

<h2 id="submission">Submission</h2>

<p>
    Submit the following to your personal Git repository, in a subdirectory named `assignments/a2':
</p>

<ul>
    <li>All files required to build and run your project (including source, images).</li>
    <li>A gradle.build that will build, then execute, your project by typing only "gradle build" followed by "gradle run" from the command line.</li>
    <li>A saved drawing from your application that can be loaded to demonstrate your load and save capabilities.</li>
    <li>A <code>readme.md</code> file that documents your application.</li>
</ul>

<h2 id="assessment">Assessment</h1>

<p>
    <ul>
        <li>Late assignments will not be accepted. </li>
        <li>TAs will grade on Windows or Mac, using your README as a guideline. If you include more than 10% in additional features, they will only grade the first ones that are submitted.</li>
    </ul>
</p>

<p>Your submission will be assessed roughly as follows:</p>

<dl class="assess">
    <dt>5%</dt><dd>Gradle compiles and runs program.</dd>
    <dt>60%</dt><dd>Functional requirements, described above.</dd>
    <dt>25%</dt><dd>Appropriate use of widgets to meet the requirements above.</dd>
    <dt>10%</dt><dd>Additional features from the list above.</dd>
</dl>

<h2>Versions</h2>

<ul>
    <li>1.0. January 29. Initial release.</li>
    <li>1.1. January 30. Removed window resizing requirement. Switched makefile to gradle.</li>
    <li>1.2. February 4. Removed dockable toolbars as a feature.</li>
</ul>

</main>    

</body>

</html>