var font;
var input;
var button;
var vehicles = [];
var maxX = 0;

function preload() {
  font = loadFont('AvenirNextLTPro-Demi.otf');
}

function setup() {
  createCanvas(646, 300);
  background(51);

  input = createInput();
  input.position(20, height + 20);

  button = createButton('submit');
  button.position(input.x + input.width, height + 20);
  button.mousePressed(newWord);

  var points = font.textToPoints('try it!', 100, 200, 192, {
    sampleFactor: 0.25
  });

  for (var i = 0; i < points.length; i++) {
    if (points[i].x > maxX) {
      maxX = points[i].x;
    }
    var pt = points[i];
    var vehicle = new Vehicle(pt.x, pt.y);
    vehicles.push(vehicle);
  }
}

function draw() {
  background(51);
  for (var i = 0; i < vehicles.length; i++) {
    var v = vehicles[i];
    v.behaviors();
    v.update();
    v.show();
  }
}

function newWord() {
  maxX = 0;
  var newPoints = font.textToPoints(input.value(), 100, 200, 192, {
    sampleFactor: 0.25
  });

  var i;
  for (i = 0; i < newPoints.length; i++) {
    if (newPoints[i].x > maxX) {
      maxX = newPoints[i].x;
    }
    if (i < vehicles.length) {
      vehicles[i].setTarget(newPoints[i].x, newPoints[i].y);
    } else {
      vehicles.push(new Vehicle(newPoints[i].x, newPoints[i].y));
    }
  }
  while (i < vehicles.length) {
    vehicles.pop();
  }

  resizeCanvas(maxX + 100, height);
}
