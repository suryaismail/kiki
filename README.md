# Courier coding challenge

Code for the Courier coding challenge for Everest Engineering.

### Development environment

* Java 11 (adoptopenjdk-11.jdk)
* Apache Maven 3.8.6
* logback-classic
* Junit 5

### Assumptions

* All input values are valid
  * package ids and offer codes are strings without whitespace
  * all other values are positive integers of reasonable size so the calculation doesn't overflow Integer.MAX_VALUE


From the assignment

``We should prefer heavier packages when there are multiple shipments with the same no. of packages. If the weights are also the same, preference should be given to the shipment which can be delivered first.``

  * assume the weight refers to the  __total__  weight of the delivery
  * so if we have the following packages with maxWeight=100
    * P1=60kg:10km
    * P2=70kg:10km
    * P3=25kg:10km
    * P4=15kg:15km
    * P5=15kg:10km
  * The following options are possible
    * __Option1__
      * P2=70kg:10km, P4=15kg:15km, P5=15kg:10km
      * totalWeight=100kg, distance 15km
    * __Option2__
      * P1=60kg:10km, P5=15kg:10km, P3=25kg:10km
      * totalWeight=100kg, distance 10km
  * The second option is chosen since
     * the number of packages is the same
     * the  __total weights__  are the  __same__  (even though option 1 has the heavier package)
      * Option2 will take less time to deliver
      
Floating point values are such a  __joy__  to work with. The assignment says round down so the code does so where needed to match the sample. Discount and cost can also have decimal, but the code doesn't round those down, just prints to 2 decimal points.

### Building

mvn clean package

### Running

The program accepts input from stdin and writes to stdout, so the user
can just type/copy and paste the input to the console.

``java -jar target/kiki.jar``

Or, to run using files

``cat <inputfile> |  java -jar target/kiki.jar > outputfile.txt``

Sample input files are in
docs/test/

