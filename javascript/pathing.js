// Neuranode arborization algorithm and node development by George Bekheet V1.2 with Organic aborization

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
  $(".neuranode-button").click(function() {
    // s = Snap(canvasX, canvasY);
    s = Snap(".neuranode-svg");

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

   //Angle is variable.
    var polar = function(r,theta){

      pi = Math.PI;
      x = fixQuadX(r*Math.cos(theta*pi));
      y = fixQuadY(r*Math.sin(theta*pi));

      return [x,y];

    }

  });
}
