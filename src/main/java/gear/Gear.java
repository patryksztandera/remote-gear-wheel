package gear;

class Gear {

     double coordinateX;
     double coordinateY;
     double bottomRadius;
     double topRadius;
     double velocity;
     String direction;

     Gear(double coordinateX, double coordinateY, double bottomRadius, double topRadius) {

            this.coordinateX = coordinateX;
            this.coordinateY = coordinateY;
            this.bottomRadius = bottomRadius;
            this.topRadius = topRadius;
     }

     Gear(double coordinateX, double coordinateY, double bottomRadius, double topRadius, double velocity) {

            this.coordinateX = coordinateX;
            this.coordinateY = coordinateY;
            this.bottomRadius = bottomRadius;
            this.topRadius = topRadius;
            this.velocity = velocity;

            if (velocity>0){
                this.direction = "R"; //right
            }
            else if (velocity<0){
                this.direction = "L"; //left
            }
            else {
                this.direction = "roll at rest"; // (velocity = 0)
            }
     }

     void setVelocity(double velocity) { this.velocity = velocity; }

     void setDirection(String direction) { this.direction = direction; }
}
