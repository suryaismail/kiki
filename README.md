# Courier coding challenge

Code for the Courier coding challenge for Everest Engineering.

## Change log


KikiDeliveryService.estimate does not take input from the command line.
It takes an array of input lines and returns and array of output lines
KikisDeliveryApp takes input from the command line
App is renamed for clarity

Offers read from a json file. Offer refactored to an interface and an implementation







--------

Coding practices:

Code is easy to understand but it has few issues.

Code should've been organized in a better way.

Basic input validation added but can be improved.

Code is not extensible & fully testable.

SOLID principles not followed.



KikisDeliveryService is a bit difficult to understand how code works. This should be refactored properly.

KikisDeliveryService class has all core logic of the app which can be split into separate & meaningful classes to segregate the responsibilities(Cost calculation & Time calculation) etc.

There are few utils methods which can have their own Utils class.
Class design can be improved.

DeliveryCostEstimation:


DeliveryTimeEstimation:

But code is not optimized and not easy to follow the code flow. This should be refactored and optimized



### Development environment

* Java 11 (adoptopenjdk-11.jdk)
* Apache Maven 3.8.6
* logback-classic
* Junit 5
* jackson

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

