var snake;

function setup() {
  createCanvas(600, 600);
  background(51);
  snake = new Snake(10);
  frameRate(10);
}

function draw() {
  background(51);
  snake.update();
  snake.show()
}

function keyPressed() {
  if (keyCode == 32) {

    snake.add(snake.x - 10, snake.y);
    console.log(snake.cells);
  }
  snake.setDir(keyCode)
}
