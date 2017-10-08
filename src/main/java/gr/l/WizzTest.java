package gr.l;

import java.util.*;


public class WizzTest {


    public static void main(String[] args) throws InterruptedException {

        System.setProperty("webdriver.gecko.driver", "C:\\Users\\lena\\Downloads\\geckodriver-v0.19.0-win64\\geckodriver.exe");
        // INPUT fromDest and put it into a list
        ArrayList<String> fromDest = new ArrayList<String>();
        Scanner scan = new Scanner(System.in);
        System.out.println("Input destinations you want to go from:");
        String input = scan.nextLine();

        while (!input.equals("END")) {
            fromDest.add(input);
            input = scan.nextLine();
        }
        //input toDest
        System.out.print("Enter toDest ");
        String toDest = scan.nextLine();
        //input date
        System.out.print("Enter date (MM/DD/YYYY) ");
        String selectdata = scan.nextLine();
        //input number of Adults
        System.out.print("Enter numAdults ");
        int numAdults = scan.nextInt();
        //close input
        scan.close();

        //Format date provided
        int month = DateFormatter.monthIntFormatter(selectdata);
        int year = DateFormatter.yearIntFormatter(selectdata);
        int day = DateFormatter.dayIntFormatter(selectdata);
        String monthStr = DateFormatter.monthStrFormatter(selectdata);

        //Print date selected and starting test line
        System.out.println("starting date is" + monthStr + " " + day + ". Test in progress...");


        //run priceForFlight
        Pair cheapestDestination = fromDest.stream()
                .map(dest -> new Pair(dest, WizzPriceFinder.priceForFlight(dest, toDest, numAdults, year, month, day, monthStr)))
                .min(Comparator.comparing((Pair pair) -> getPriceInt(pair.getValue())))
                .orElseThrow(() -> new IllegalStateException("destination is not found"));

        int findMinPrice = getPriceInt(cheapestDestination.getValue());
        String findDest = cheapestDestination.getKey();



//        for (int i = 0; i < fromDest.size(); i++) {
//            String priceText = WizzPriceFinder.priceForFlight(fromDest.get(i), toDest, numAdults, year, month, day, monthStr);
//            int priceInt = getPriceInt(priceText);
//            System.out.println(priceText + " from " + fromDest.get(i) + " to " + toDest);
//            if (findMinPrice == 0 || findMinPrice > priceInt) {
//                findMinPrice = priceInt;
//                findDest = fromDest.get(i);
//            }
//        }
        System.out.println("Min price is " + findMinPrice + " from " + findDest + " to " + toDest);
    }

    private static int getPriceInt(String priceText) {
        return Integer.parseInt(priceText.replaceAll("[^0-9]", ""));
    }




}

