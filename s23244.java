import java.io.*;
import java.lang.invoke.SwitchPoint;
import java.util.*;

public class s23244 {
    public static void main(String[] args) {
        if (args[0].equals("save")) {
            Airplane[] myAirplane = new Airplane[10];
            myAirplane = create(aircraftType.fighter, myAirplane, 0);
            myAirplane = create(aircraftType.fighter, myAirplane, 1);
            myAirplane = create(aircraftType.passenger, myAirplane, 2);
            myAirplane = create(aircraftType.passenger, myAirplane, 3);
            myAirplane = create(aircraftType.transporter, myAirplane, 4);
            myAirplane = create(aircraftType.transporter, myAirplane, 5);
            myAirplane = create(aircraftType.aeroplane, myAirplane, 6);
            myAirplane = create(aircraftType.aeroplane, myAirplane, 7);
            myAirplane = create(aircraftType.paraglider, myAirplane, 8);
            myAirplane = create(aircraftType.paraglider, myAirplane, 9);
            writeToFile(myAirplane, "aircraft.txt");
        } else if (args[0].equals("load")) {
            String[] samolot = readFromFile();
            if (args.length > 1) {
                Airplane[] temp = multiplyAirCraft(args[1], samolot);
                writeToFile(temp, "newAircraft.txt");
                int[] points = new int[temp.length];
                points = positions(temp);
                for(int i = 0;i<points.length;i++){
                    System.out.println(points[i]);
                }
            }
        }
    }

    private static int[] positions(Airplane[] temp) {
        int length = temp.length;
        int[] distance = new int[length];
        for (int i = 0; i < length; i++) {
            int temp1 = 0;
            int positionx;
            int positiony;
            positionx = temp[i].positionx;
            positiony = temp[i].positiony;
            int pitch = temp[i].pitch;
            if ((positionx < 0 &&positiony < 0) || (positionx > 0 && positiony > 0)) {
                if (positionx < positiony) {
                    temp1 = Math.abs((positionx)) - Math.abs((positiony));
                } else {
                    temp1 = Math.abs((positiony)) - Math.abs((positionx));
                }
            } else {
                temp1 = Math.abs((positionx)) + Math.abs((positiony));
            }
            temp1 = Math.abs(temp1);
            temp1 += pitch;
            distance[i] = temp1;
            return distance;
        }
        return distance;
    }

    private static Airplane[] multiplyAirCraft(String arg,String[] airplane) {
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
        temp = getSpecifiedAircraft(temp3,temp2,arr,index);
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
                transporter.convert(temp);
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
                paraglider.convert(temp);
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

    private static String[] readFromFile() {
        int i = 0;
        String[] line = new String[10];
        try {
            Scanner scanner = new Scanner(new File("out.txt"));
            while (scanner.hasNextLine()) {
                line[i] = scanner.nextLine();
                i++;
            }
        } catch (FileNotFoundException err) {
            System.out.println(err);
        }
        return line;
    }

    public static Airplane[] create(aircraftType type, Airplane[] myAirplane, int i) {
        switch (type) {
            case passenger -> {
                Passenger passenger = new Passenger();
                passenger.soulsLimit = getRand(50, 150);
                passenger.soulsOnBoard = getRand(50, passenger.soulsLimit);
                getDetails(passenger, type);
                myAirplane[i] = passenger;
                return myAirplane;
            }
            case transporter -> {
                Transporter transporter = new Transporter();
                getDetails(transporter, type);
                myAirplane[i] = transporter;
                return myAirplane;
            }
            case aeroplane -> {
                Aeroplane aeroplane = new Aeroplane();
                getDetails(aeroplane, type);
                myAirplane[i] = aeroplane;
                return myAirplane;
            }
            case paraglider -> {
                paraglider paraglider = new paraglider();
                getDetails(paraglider, type);
                myAirplane[i] = paraglider;
                return myAirplane;
            }
            case fighter -> {
                Fighter fighter = new Fighter();
                fighter.missiles = getRand(4, 16);
                fighter.hasFlares = getRand(0, 1000) > 500;
                getDetails(fighter, type);
                myAirplane[i] = fighter;
                return myAirplane;
            }
        }
        return myAirplane;
    }

    public static Airplane getDetails(Airplane airplane, aircraftType type) {
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
            case passenger, transporter -> getRand(5000, 11000);
            case aeroplane -> getRand(50, 1000);
            case paraglider -> getRand(50, 200);
            case fighter -> getRand(7000, 20000);
        };
    }

    private static sizeClass getSizeClass(int weight) {
        if (weight < 500) {
            return sizeClass.verySmall;
        } else if (weight < 1000) {
            return sizeClass.small;
        } else if (weight < 1500) {
            return sizeClass.medium;
        } else if (weight < 2000) {
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

    public Airplane(){

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

    public void randomisePosition() {
        this.positionx = getRand(-1000, 1000);
        this.positiony = getRand(-1000, 1000);
        this.pitch = getPitch(this.type);
    }

    private static int getPitch(aircraftType type) {
        return switch (type) {
            case passenger, transporter -> getRand(5000, 11000);
            case aeroplane -> getRand(50, 1000);
            case paraglider -> getRand(50, 200);
            case fighter -> getRand(7000, 20000);
        };
    }

    private static int getRand(int min, int max) {
        return (int) (min + Math.random() * (max - min));
    }
}

class Fighter extends Airplane {
    int missiles;
    boolean hasFlares;

    public Fighter(){}

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

    public Passenger(){}

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
    public String crateData() {
        String data = super.crateData();
        data += "\n";
        return data;
    }
    public Transporter(){}

    public Transporter(Airplane airplane) {
        super(airplane);
    }

    public Airplane convert(Airplane airplane) {
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

class Aeroplane extends Airplane {
    public String crateData() {
        String data = super.crateData();
        data += "\n";
        return data;
    }

    public Aeroplane(){}

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
    public String crateData() {
        String data = super.crateData();
        data += "\n";
        return data;
    }

    public paraglider(){}

    public paraglider(Airplane airplane) {
        super(airplane);
    }

    public Airplane convert(Airplane airplane) {
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
