let angle;
let w;
let maxD;
let xAngle;

function setup() {
  createCanvas(300, 300, WEBGL);

  angle = 0;
  w = 15;
  maxD = dist(0, 0, width / 2, height / 2);
  xAngle = -atan(1 / sqrt(2));
}

function draw() {
  background(51);
  ortho(-225, 225, -225, 225, 0, 600);
  rotateX(xAngle);
  rotateY(-QUARTER_PI);

  let d;
  let offset;
  let h;
  for (let z = 0; z < height; z += w) {
    for (let x = 0; x < width; x += w) {
      push();
      d = dist(x, z, width * 0.5, height * 0.5);
      offset = map(d, 0, maxD, -PI, PI);
      h = map(sin(angle + offset), -1, 1, 50, 200);
      translate(x - width * 0.5, 0, z - height * 0.5);
      normalMaterial();
      box(w, h, w);

      pop();
    }
  }

  angle -= 0.1;
}
