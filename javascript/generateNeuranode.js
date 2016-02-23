// Neuranode arborization algorithm and node development by George Bekheet V1.2 with Organii aborization

// set up workspace
var workspaceX = 1200;
var workspaceY = 1200;
var canvasX = workspaceX * 5 / 6;
var canvasY = workspaceY * 5 / 6;

// colors and stroke widths
var backdropStrokeColor = "#000";
var backdropFillColor = "#FFF";
var backdropStrokeWidth = 5;
var color1 = "#f63";
var boundsStroke = 2;

var qArray = [0, 0, 0, 0, 0, 0, 0, 0];

$(document).ready(function() {
  // $(".neuranode-button").click(generateNeuranode);
});

var generateNeuranode = function() {
  // s = Snap(canvasX, canvasY);
  s = Snap(".neuranode-svg");

  // initialize variables
  for (var i = 0; i < 8; i++) {
    qArray[i] = $(".trait-" + (i + 1)).val();
  }

  // circle backdrop
  var circleBackdrop = s.circle(fixQuadX(0), fixQuadY(0), canvasX / 2);
  circleBackdrop.attr({
    fill: backdropFillColor,
    stroke: backdropStrokeColor,
    strokeWidth: backdropStrokeWidth
  });

  // every other octant, will connect the central node to the quadrant primary nodes.
  var octArray = [
    [
      [0, 100, 200, 250, 325, 350, 425], // oct1X
      [0, 100, 200, 75, 175, 300, 75] // oct1Y
    ],
    [
      [200, 75, 175, 300, 75], // oct2X
      [200, 250, 325, 350, 425] // oct2Y
    ],
    [
      [0, -100, -200, -75, -175, -300, -75], // oct3X
      [0, 100, 200, 250, 325, 350, 425] // oct3Y
    ],
    [
      [-200, -250, -325, -350, -425], // oct4X
      [200, 75, 175, 300, 75] // oct4Y
    ],
    [
      [0, -100, -200, -250, -325, -350, -425], // oct5X
      [0, -100, -200, -75, -175, -300, -75] // oct5Y
    ],
    [
      [-200, -75, -175, -300, -75], // oct6X
      [-200, -250, -325, -350, -425] // oct6Y
    ],
    [
      [0, 100, 200, 250, 325, 350, 425], // oct7X
      [0, -100, -200, -75, -175, -300, -75] // oct7Y
    ],
    [
      [200, 75, 175, 300, 75], // oct8X
      [-200, -250, -325, -350, -425] // oct8Y
    ]
  ];

  for (var octant = 0; octant < octArray.length; octant++) {
    // node drawing
    var offset = (octant % 2 == 0) ? 0 : 2;
    for (nodeCounter = 0; nodeCounter <= qArray[octant] + offset; nodeCounter++) {
      octagonalNodeArray(
        fixQuadX(octArray[octant][0][nodeCounter]),
        fixQuadY(octArray[octant][1][nodeCounter])
      );
    }
    // linear arborization
    for (var lineCounter = 0; lineCounter <= qArray[octant] + offset; lineCounter++) {
      oA(octArray[octant][0][lineCounter],
        octArray[octant][1][lineCounter],
        octArray[octant][0][lineCounter + 1],
        octArray[octant][1][lineCounter + 1]
      );
    }
  }

  // NOTE: don't know what to name this array
  // TODO: change name
  var array2 = [
    // visual enhancement of the illustrator
    [0, -500, 0, 500],      // y-axis
    [-500, 0, 500, 0],      // x-axis
    [-500, -500, 500, 500], // diag yx
    [-500, 500, 500, -500], // diag y - x
    // bounds for octagonal approach that will be taken
    [500, 0, 353, 353],     // oct 1 quad 1
    [353, 353, 0, 500],     // oct 2 quad 2
    [0, 500, -353, 353],    // oct 1 quad 2
    [-353, 353, -500, 0],   // oct 2 quad 2
    [-500, 0, -353, -353],  // oct 1 quad 3
    [-353, -353, 0, -500],  // oct 2 quad 3
    [0, -500, 353, -353],   // oct 1 quad 4
    [353, -353, 500, 0]     // oct 2 quad 4
  ];

  for (var i = 0; i < array2.length; i++) {
    s.line(fixQuadX(array2[i][0]),
      fixQuadY(array2[i][1]),
      fixQuadX(array2[i][2]),
      fixQuadY(array2[i][3])
    ).attr({
      fill: "none",
      stroke: color1,
      strokeWidth: boundsStroke
    });
  }
};

var fixQuadX = function(coordinateX) {
  if (coordinateX > 0) {
    return coordinateX + (canvasX / 2);
  } else {
    return (canvasX / 2) - Math.abs(coordinateX);
  }
}

var fixQuadY = function(coordinateY) {
  if (coordinateY > 0) {
    return (canvasY / 2) - coordinateY;
  } else {
    return (canvasY / 2) + Math.abs(coordinateY);
  }
}

// return random number between min and max
var randomInterval = function(min, max) {
  return Math.floor(Math.random() * (max - min) + min);
}

var octagonalNodeArray = function(xCoordinate, yCoordinate) {
  s.circle(xCoordinate,yCoordinate, 10)
    .attr({
      fill: color1,
      stroke: color1,
      strokeWidth: 8
    });
}

// draw lines between appropriate nodes (linear arborization function)
var lineDrawer = function(x1, y1, x2, y2) {
  s.line(fixQuadX(x1), fixQuadY(y1), fixQuadX(x2), fixQuadY(y2)).attr({
    fill: "none",
    stroke: color1,
    strokeWidth: 10
  });
}

// randomization of organic arborization function
var randomization = function() {
  return parseFloat(Math.random() * 10);
}

//Organic Arborization Algorithm
var oA = function(x1, y1, x2, y2) {

  x1 = fixQuadX(x1);
  x2 = fixQuadX(x2);
  y1 = fixQuadY(y1);
  y2 = fixQuadY(y2);

  // determine our distances from static points
  var rando = Math.random() * 10;
  oneThirdX = ((x1 * (2 / 3)) + (x2 * (1 / 3)));
  oneThirdY = ((y1 * (2 / 3)) + (y2 * (1 / 3)));
  twoThirdX = ((x1 * (1 / 3)) + (x2 * (2 / 3)));
  twoThirdY = ((y1 * (1 / 3)) + (y2 * (2 / 3)));

  // get proper translation in each quadrant
  newControlPointX1 = randomInterval(x1, oneThirdX);
  newControlPointY1 = randomInterval(y1, oneThirdY);
  newControlPointX2 = randomInterval(twoThirdX, x2);
  newControlPointY2 = randomInterval(twoThirdY, y2);

  // path function between two nodes/regions of interest
  s.path("M" + x1.toString() +
    "," + y1.toString() +
    ",C" + newControlPointX1.toString() +
    "," + newControlPointY1.toString() +
    "," + newControlPointX2.toString() +
    "," + newControlPointY2.toString() +
    "," + x2.toString() +
    "," + y2.toString()).attr({
    fill: "none",
    stroke: color1,
    strokeWidth: 10
  });
}
