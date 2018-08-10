function Snake(r) {
  this.x = 0;
  this.y = 0;
  this.xspeed = 1;
  this.yspeed = 0;
  this.r = r;

  this.cells = [createVector(0, 0)];

  this.setDir = function(newDir) {
    switch (newDir) {
      case DOWN_ARROW:
        this.xspeed = 0;
        this.yspeed = 1;
        break;
      case UP_ARROW:
        this.xspeed = 0;
        this.yspeed = -1;
        break;
      case LEFT_ARROW:
        this.xspeed = -1;
        this.yspeed = 0;
        break;
      case RIGHT_ARROW:
        this.xspeed = 1;
        this.yspeed = 0;
        break;
    }
  }

  this.add = function(x, y) {
    this.cells.push(createVector(x, y));
  }

  this.update = function() {
    for (var i = 0; i < this.cells.length - 1; i++) {
      this.cells[i] = this.cells[i + 1];
    }
    this.cells[this.cells.length - 1]] = createVector(this.x, this.y);
    
    this.x += this.xspeed * this.r;
    this.y += this.yspeed * this.r;

    this.x = constrain(this.x, 0, width - this.r);
    this.y = constrain(this.y, 0, height - this.r);


  }

  this.show = function() {
    fill(255);
    for (var i = 0; i < this.cells.length; i++) {
      rect(this.cells[i].x, this.cells[i].y, this.r, this.r)
    }
  }
}
