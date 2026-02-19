package trafficlight;

public class TrafficLightTest {

  public static void main(String[] args) {

    TrafficLight[] lights = TrafficLight.values();

    //for-each loop ot access Enum constants and their values
    for (TrafficLight light : lights) {

      System.out.println("Light: " + light.toString() + " Action: " + light.getAction());

    }

  }

}
