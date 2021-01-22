import java.io.*;
import java.util.*;

public class s23244 {
    public static void main(String[] args) {
        if (args[0].equals("save")) {
            Airplane[] myAirplane = new Airplane[10];
            myAirplane = createNewAirplane(aircraftType.fighter, myAirplane, 0);
            myAirplane = createNewAirplane(aircraftType.fighter, myAirplane, 1);
            myAirplane = createNewAirplane(aircraftType.passenger, myAirplane, 2);
            myAirplane = createNewAirplane(aircraftType.passenger, myAirplane, 3);
            myAirplane = createNewAirplane(aircraftType.transporter, myAirplane, 4);
            myAirplane = createNewAirplane(aircraftType.transporter, myAirplane, 5);
            myAirplane = createNewAirplane(aircraftType.aeroplane, myAirplane, 6);
            myAirplane = createNewAirplane(aircraftType.aeroplane, myAirplane, 7);
            myAirplane = createNewAirplane(aircraftType.paraglider, myAirplane, 8);
            myAirplane = createNewAirplane(aircraftType.paraglider, myAirplane, 9);
            writeToFile(myAirplane, "aircraft.txt");
        } else if (args[0].equals("load")) {
            String[] samolot = readFromFile();
            if (args.length > 1) {
                Airplane[] temp = multiplyAirCraft(args[1], samolot);
                writeToFile(temp, "newAircraft.txt");
                int[][] goodPoints = new int[11][1];
                for (int i = 0; i < temp.length; i++) {
                    int position = positions(temp[i]);
                    int location = getPointsInReach(position);
                    int lengthOfArray = goodPoints[location].length;
                    int[] temp1 = goodPoints[location];
                    goodPoints[location] = new int[(lengthOfArray + 1)];
                    for (int y = 0; y < temp1.length; y++) {
                        goodPoints[location][y] = temp1[y];
                    }
                    goodPoints[location][(lengthOfArray - 1)] = position;
                }
                String data = GetGoodPointsFile(goodPoints);
                writeToFile(data, "pointsList.txt");
            }
        }
    }

    private static String GetGoodPointsFile(int[][] points) {
        String data = "";
        for (int i = 1; i < points.length; i++) {
            data += "potęga: " + i + " mniejsze niż: " + Math.pow(3, i) + " ma:" + (points[i].length - 1) + " samolotow" + " \n";
        }
        data += "\n--------------------------------\n\n";
        for (int i = 1; i < points.length; i++) {
            if (points[i].length != 1) {
                data += "potęga: " + i + " mniejsze niż: " + Math.pow(3, i) + "\n";
                for (int k = 0; k < (points[i].length - 1); k++) {
                    data += points[i][k] + " \n";
                }
            }
        }
        return data;
    }

    private static int getPointsInReach(int position) {
        int location = 0;
        for (int i = 1; i < 11; i++) {
            if (position < Math.pow(3, i)) {
                location = i;
                return location;
            }
        }

        return location;

    }

    private static int positions(Airplane temp) {
        int temp1 = 0;
        int positionx;
        int positiony;
        positionx = temp.positionx;
        positiony = temp.positiony;
        int pitch = temp.pitch;
        temp1 = (int) Math.abs(Math.sqrt(Math.pow(positionx, 2) + Math.pow(positiony, 2) + Math.pow(pitch, 2)));
        return temp1;
    }

    private static Airplane[] multiplyAirCraft(String arg, String[] airplane) {
        int multiplayer = Integer.parseInt(arg);
        int placeInArray = 0;
        Airplane[] tempList = new Airplane[10 * multiplayer];
        System.out.println(multiplayer);
        for (int i = 0; i < 10; i++) {
            for (int k = 0; k < multiplayer; k++) {
                tempList = toAirCraft(airplane[i], placeInArray, tempList);
                tempList[placeInArray].positiony = getRand(-1500, 1500);
                tempList[placeInArray].positionx = getRand(-1500, 1500);
                tempList[placeInArray].pitch = getPitch(tempList[placeInArray].type);
                placeInArray++;
            }
        }
        return tempList;
    }

    private static Airplane[] toAirCraft(String airplane, int index, Airplane[] arr) {
        Airplane[] temp;
        String[] temp2 = airplane.split(",");
        Airplane temp3 = new Airplane();
        temp3.convert(temp2);
        temp = getSpecifiedAircraft(temp3, temp2, arr, index);
        return temp;
    }

    private static Airplane[] getSpecifiedAircraft(Airplane temp, String[] temp2, Airplane[] arr, int i) {
        switch (temp.type) {
            case passenger -> {
                Passenger passenger = new Passenger();
                passenger.convert(temp, temp2);
                arr[i] = passenger;
                return arr;
            }
            case transporter -> {
                Transporter transporter = new Transporter();
                transporter.convert(temp, temp2);
                arr[i] = transporter;
                return arr;
            }
            case aeroplane -> {
                Aeroplane aeroplane = new Aeroplane();
                aeroplane.convert(temp, temp2);
                arr[i] = aeroplane;
                return arr;
            }
            case paraglider -> {
                paraglider paraglider = new paraglider();
                paraglider.convert(temp, temp2);
                arr[i] = paraglider;
                return arr;
            }
            case fighter -> {
                Fighter fighter = new Fighter();
                fighter.convert(temp, temp2);
                arr[i] = fighter;
                return arr;
            }
        }
        return arr;
    }

    private static void writeToFile(Airplane[] myAirplane, String fileName) {
        try {
            FileWriter myWriter = new FileWriter(fileName);
            for (int i = 0; i < myAirplane.length; i++) {
                myWriter.write(myAirplane[i].crateData());
            }
            myWriter.close();
            System.out.println("Udalo sie zapisac do pliku!");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    private static void writeToFile(String data, String fileName) {
        try {
            FileWriter myWriter = new FileWriter(fileName);
            myWriter.write(data);
            myWriter.close();
            System.out.println("Udalo sie zapisac do pliku!");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    private static String[] readFromFile() {
        int i = 0;
        String[] line = new String[10];
        try {
            Scanner scanner = new Scanner(new File("aircraft.txt"));
            while (scanner.hasNextLine()) {
                line[i] = scanner.nextLine();
                i++;
            }
        } catch (FileNotFoundException err) {
            System.out.println(err);
        }
        return line;
    }

    public static Airplane[] createNewAirplane(aircraftType type, Airplane[] myAirplane, int i) {
        switch (type) {
            case passenger -> {
                Passenger passenger = new Passenger();
                passenger.soulsLimit = getRand(50, 150);
                passenger.soulsOnBoard = getRand(50, passenger.soulsLimit);
                getBasicDetails(passenger, type);
                myAirplane[i] = passenger;
                return myAirplane;
            }
            case transporter -> {
                Transporter transporter = new Transporter();
                transporter.maximumLoad = getRand(1000, 10000);
                transporter.currentLoad = getRand(1000, transporter.maximumLoad);
                getBasicDetails(transporter, type);
                myAirplane[i] = transporter;
                return myAirplane;
            }
            case aeroplane -> {
                Aeroplane aeroplane = new Aeroplane();
                getBasicDetails(aeroplane, type);
                myAirplane[i] = aeroplane;
                return myAirplane;
            }
            case paraglider -> {
                paraglider paraglider = new paraglider();
                getBasicDetails(paraglider, type);
                paraglider.canopySpan = getRand(5, 20);
                myAirplane[i] = paraglider;
                return myAirplane;
            }
            case fighter -> {
                Fighter fighter = new Fighter();
                fighter.missiles = getRand(4, 16);
                fighter.hasFlares = getRand(0, 1000) > 500;
                getBasicDetails(fighter, type);
                myAirplane[i] = fighter;
                return myAirplane;
            }
        }
        return myAirplane;
    }

    public static Airplane getBasicDetails(Airplane airplane, aircraftType type) {
        airplane.positionx = getRand(-1000, 1000);
        airplane.positiony = getRand(-1000, 1000);
        airplane.vector = getRand(-180, 180);
        airplane.callSign = getCallSign();
        airplane.pitch = getPitch(type);
        airplane.speed = getSpeed(type);
        airplane.weight = getWeight(type);
        airplane.sizeClass = getSizeClass(airplane.weight);
        airplane.type = type;
        return airplane;
    }

    private static String getCallSign() {
        int number = getRand(1000, 10000);
        return "FA" + number;
    }

    private static int getPitch(aircraftType type) {
        return switch (type) {
            case passenger, transporter -> getRand(0, 10000);
            case aeroplane -> getRand(0, 1000);
            case paraglider -> getRand(0, 100);
            case fighter -> getRand(0, 12000);
        };
    }

    private static sizeClass getSizeClass(int weight) {
        if (weight < 800) {
            return sizeClass.verySmall;
        } else if (weight < 2000) {
            return sizeClass.small;
        } else if (weight < 7000) {
            return sizeClass.medium;
        } else if (weight < 15000) {
            return sizeClass.large;
        } else {
            return sizeClass.veryLarge;
        }
    }

    private static int getWeight(aircraftType type) {
        return switch (type) {
            case passenger, transporter -> getRand(162000, 400000);
            case aeroplane -> getRand(300, 1000);
            case paraglider -> getRand(55, 130);
            case fighter -> getRand(10000, 20000);
        };
    }

    private static int getSpeed(aircraftType type) {
        return switch (type) {
            case passenger, transporter -> getRand(500, 1200);
            case aeroplane -> getRand(50, 150);
            case paraglider -> getRand(20, 100);
            case fighter -> getRand(1000, 4000);
        };
    }

    private static int getRand(int min, int max) {
        return (int) (min + Math.random() * (max - min));
    }
}

class Airplane {
    int positionx;
    int positiony;
    int vector;
    String callSign;
    int pitch;
    int speed;
    int weight;
    sizeClass sizeClass;
    aircraftType type;

    public Airplane() {

    }

    public Airplane(Airplane airplane) {
        this.positionx = airplane.positionx;
        this.positiony = airplane.positiony;
        this.vector = airplane.vector;
        this.callSign = airplane.callSign;
        this.pitch = airplane.pitch;
        this.speed = airplane.speed;
        this.weight = airplane.weight;
        this.sizeClass = airplane.sizeClass;
        this.type = airplane.type;
    }

    public String crateData() {
        String data = "";
        data += this.type + ",";
        data += this.positionx + ",";
        data += this.positiony + ",";
        data += this.vector + ",";
        data += this.callSign + ",";
        data += this.pitch + ",";
        data += this.speed + ",";
        data += this.weight + ",";
        data += this.sizeClass + ",";
        return data;
    }

    public Airplane convert(String[] temp1) {
        this.type = aircraftType.valueOf(temp1[0]);
        this.positionx = Integer.parseInt(temp1[1]);
        this.positiony = Integer.parseInt(temp1[2]);
        this.vector = Integer.parseInt(temp1[3]);
        this.callSign = temp1[4];
        this.pitch = Integer.parseInt(temp1[5]);
        this.speed = Integer.parseInt(temp1[6]);
        this.weight = Integer.parseInt(temp1[7]);
        this.sizeClass = sizeClass.valueOf(temp1[8]);
        return this;
    }

}

class Fighter extends Airplane {
    int missiles;
    boolean hasFlares;

    public Fighter() {
    }

    public Fighter(Airplane airplane, int missiles, boolean hasFlares) {
        super(airplane);
        this.missiles = missiles;
        this.hasFlares = hasFlares;
    }

    public String crateData() {
        String data = super.crateData();
        data += this.missiles + ",";
        data += this.hasFlares;
        data += "\n";
        return data;
    }

    public Airplane convert(Airplane airplane, String[] temp) {
        this.type = airplane.type;
        this.positionx = airplane.positionx;
        this.positiony = airplane.positiony;
        this.vector = airplane.vector;
        this.callSign = airplane.callSign;
        this.pitch = airplane.pitch;
        this.speed = airplane.speed;
        this.weight = airplane.weight;
        this.sizeClass = airplane.sizeClass;
        this.missiles = Integer.parseInt(temp[9]);
        this.hasFlares = Boolean.parseBoolean(temp[10]);
        return this;
    }
}

class Passenger extends Airplane {
    int soulsLimit;
    int soulsOnBoard;

    public Passenger() {
    }

    public Passenger(Airplane airplane, int soulsLimit, int soulsOnBoard) {
        super(airplane);
        this.soulsLimit = soulsLimit;
        this.soulsOnBoard = soulsOnBoard;
    }

    public String crateData() {
        String data = super.crateData();
        data += this.soulsLimit + ",";
        data += this.soulsOnBoard;
        data += "\n";
        return data;
    }

    public Airplane convert(Airplane airplane, String[] temp) {
        this.type = airplane.type;
        this.positionx = airplane.positionx;
        this.positiony = airplane.positiony;
        this.vector = airplane.vector;
        this.callSign = airplane.callSign;
        this.pitch = airplane.pitch;
        this.speed = airplane.speed;
        this.weight = airplane.weight;
        this.sizeClass = airplane.sizeClass;
        this.soulsLimit = Integer.parseInt(temp[9]);
        this.soulsOnBoard = Integer.parseInt(temp[10]);
        return this;
    }
}

class Transporter extends Airplane {
    int maximumLoad;
    int currentLoad;

    public String crateData() {
        String data = super.crateData();
        data += this.maximumLoad + ",";
        data += this.currentLoad;
        data += "\n";
        return data;
    }

    public Transporter() {
    }

    public Transporter(Airplane airplane, int currentLoad, int maximumLoad) {
        super(airplane);
        this.currentLoad = currentLoad;
        this.maximumLoad = maximumLoad;
    }

    public Airplane convert(Airplane airplane, String[] temp) {
        this.type = airplane.type;
        this.positionx = airplane.positionx;
        this.positiony = airplane.positiony;
        this.vector = airplane.vector;
        this.callSign = airplane.callSign;
        this.pitch = airplane.pitch;
        this.speed = airplane.speed;
        this.weight = airplane.weight;
        this.sizeClass = airplane.sizeClass;
        this.maximumLoad = Integer.parseInt(temp[9]);
        this.currentLoad = Integer.parseInt(temp[10]);
        return this;
    }
}

class Aeroplane extends Airplane {

    public String crateData() {
        String data = super.crateData();
        data += "\n";
        return data;
    }

    public Aeroplane() {
    }

    public Aeroplane(Airplane airplane) {
        super(airplane);
    }

    public Airplane convert(Airplane airplane, String[] temp) {
        this.type = airplane.type;
        this.positionx = airplane.positionx;
        this.positiony = airplane.positiony;
        this.vector = airplane.vector;
        this.callSign = airplane.callSign;
        this.pitch = airplane.pitch;
        this.speed = airplane.speed;
        this.weight = airplane.weight;
        this.sizeClass = airplane.sizeClass;
        return this;
    }
}

class paraglider extends Airplane {
    int canopySpan;

    public String crateData() {
        String data = super.crateData();
        data += this.canopySpan;
        data += "\n";
        return data;
    }

    public paraglider() {
    }

    public paraglider(Airplane airplane) {
        super(airplane);
    }

    public Airplane convert(Airplane airplane, String[] temp) {
        this.type = airplane.type;
        this.positionx = airplane.positionx;
        this.positiony = airplane.positiony;
        this.vector = airplane.vector;
        this.callSign = airplane.callSign;
        this.pitch = airplane.pitch;
        this.speed = airplane.speed;
        this.weight = airplane.weight;
        this.sizeClass = airplane.sizeClass;
        this.canopySpan = Integer.parseInt(temp[9]);
        return this;
    }
}

enum aircraftType {
    fighter,
    passenger,
    transporter,
    aeroplane,
    paraglider,
}

enum sizeClass {
    verySmall,
    small,
    medium,
    large,
    veryLarge,
}
