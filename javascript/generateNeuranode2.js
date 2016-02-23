// initialize canvas
var workspaceX = 1000;
var workspaceY = 1000;
var radius = workspaceX / 2;    // radius of neuranode
var center = [radius, radius];  // coordinates of center of workspace
var s;                          // Snap object

var levels = 5;                 // number of levels
var breakpoints = [];           // array to store radius of different levels
var distanceBetweenBreakpoints; // distance between breakpoints

var nodeAmountFactor = 1.3;     // create (level)^(nodeAmountFactor) nodes per level
var nodesPerLevel = [];         // array to hold number of nodes per level
var nodeArray = [[], [] ,[] ,[] ,[] ,[] ,[] ,[]]; // array to store all nodes in format [octants [levels [nodes [x, y]]]]

var inputArray = [];
var inputMax = 4;

///////////
// STYLE //
///////////

var colorPrimary = "#2c5da6";
var colorBlack = "#000000";
var colorWhite = "#FFFFFF"

var centralNodeSize = 10;
var centralNodeStyle = {
  fill: colorPrimary,
  stroke: colorBlack,
  strokeWidth: 2
}

var nodeSize = 5;
var nodeStyle = {
  fill: colorPrimary,
  stroke: colorBlack,
  strokeWidth: 2
}

var lineStyle = {
    stroke: colorBlack,
    strokeWidth: 5,
    "fill-opacity": 0
}

//////////
// MAIN //
//////////

$(document).ready(function() {
  $(".neuranode-button").click(function() {
    s = Snap(".neuranode-svg");
    getInput();
    generateBreakpoints(levels);
    generateNodesPerLevel(levels);
    generateNeuranode();
  });
  $(".random-button").click(function() {
    for (var i = 0; i < 8; i++) {
      inputArray[i] = $(".trait-" + (i + 1)).val(Math.floor(Math.random() * 5));
    }
  });
});

// generate neuranode
var generateNeuranode = function() {

  ///////////
  // FRAME //
  ///////////

  // draw concentric circles
  for (var i = 0; i < breakpoints.length; i++) {
    s.circle(radius, radius, breakpoints[i]).attr({
      "fill-opacity": 0,
      stroke: colorBlack,
      strokeWidth: 1
    });
  }

  // split octants
  var directions = [0, 45, 90, 135, 180, 225, 270, 315];
  for (var i = 0; i < directions.length; i++) {
    createLine(center, polar(radius, directions[i])).attr({
      stroke: colorBlack,
      strokeWidth: 1
    });
  }

  ////////////////
  // INITIALIZE //
  ////////////////

  // draw central node
  createNode(center, centralNodeSize);

  // iterate through all 8 octants
  for (var octant = 0; octant < 8; octant++) {
    // draw node checkpoints
    for (var level = 0; level < levels; level++) {
      // create blank array in nodeArray[octant] to store levels
      nodeArray[octant].push([]);
      // draw number of nodes on the current level, given by nodesPerLevel array
      for (var i = 0; i < nodesPerLevel[level]; i++) {
        var distanceFromCenter = distanceBetweenBreakpoints * (level + 0.5);
        var angle = (45 * octant) + ((45 / nodesPerLevel[level]) * (i + 0.5));
        var xy = polar(distanceFromCenter, angle)
        createNode(xy, nodeSize);
        nodeArray[octant][level].push([xy[0], xy[1]]);
      }
    }
  }

  //////////////////
  // CREATE PATHS //
  //////////////////

  // main branch from each octant
  var fromCenter = "M " + center[0] + " " + center[1] + " ";
  for (var octant = 0; octant < 8; octant++) {
    // start at central node
    var pathString = fromCenter;

    var probability = inputArray[octant] / inputMax;  // 0 - 100% chance

    // start at lowest level
    var level = 0;
    // move up to next level with a % chance
    while (Math.random() <= probability && level < levels) {
      var numNodesInLevel = nodeArray[octant][level].length;
      var randomNode = Math.floor(numNodesInLevel * Math.random());
      pathString += lineTo(octant, level, randomNode);
      level++;
    }
    var c = s.path(pathString).attr(lineStyle);
  }

  // var pathString = "M " + center[0] + " " + center[1];
  // pathString += lineTo(0, 0, 0) + lineTo(0, 1, 1);
}

var lineTo = function(octant, level, node) {
  // console.log(nodeArray[octant][level][node][0] + " " + nodeArray[octant][level][node][1]);
  return "L " + nodeArray[octant][level][node][0] + " " + nodeArray[octant][level][node][1] + " ";
}

// get user input to populate inputArray (0 - 4)
var getInput = function() {
  for (var i = 0; i < 8; i++) {
    inputArray[i] = $(".trait-" + (i + 1)).val();
  }
}

// populate breakpoints array
var generateBreakpoints = function(levels) {
  var interval = radius / levels;
  for (var i = 1; i <= levels; i++) {
    breakpoints.push(interval * i);
  }
  distanceBetweenBreakpoints = breakpoints[0];
  breakpoints.reverse();
}

// populate nodesPerLevel array
var generateNodesPerLevel = function(levels) {
  for (var i = 1; i <= levels; i++) {
    nodesPerLevel.push(Math.floor(Math.pow(i, nodeAmountFactor)));
  }
}

// create line using array for start and end coordinates
var createLine = function(xyStart, xyEnd) {
  return s.line(xyStart[0], xyStart[1], xyEnd[0], xyEnd[1]);
}

var createNode = function(xyArray, radius) {
  return s.circle(xyArray[0], xyArray[1], radius).attr(nodeStyle);
}

// return cartesian coordinates from given polar coordinates
var polar = function(r, theta) {
  var x = radius + (r * Math.cos(degToRad(theta)));
  var y = radius - (r * Math.sin(degToRad(theta)));
  return [x, y];
}

// converts degress to radians
var degToRad = function(deg) {
  return deg * Math.PI / 180;
}
