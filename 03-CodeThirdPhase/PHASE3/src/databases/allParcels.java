package databases;
public class allParcels {
    //3 main parcels
    public static Parcels3D a = new Parcels3D(2, 2, 4,3);
    public static Parcels3D b = new Parcels3D(2, 3, 4,4);
    public static Parcels3D c = new Parcels3D(3, 3, 3,5);
    //array with all rotations of 3 parcels
    public static Parcels3D [][] abc = {{a,changeXY(changeYZ(a)),changeYZ(a)},{b,changeYZ(b),changeXY(b),changeXY(changeYZ(b)),changeYZ(changeXY(b)),changeYZ(changeXY(changeYZ(b)))},{c}};
   
    /**
     * This method rotate a parcel changing
     * y axis with z axis
     * @param k a parcel for rotating 
     * @return the parcel rotated
     */
    public static Parcels3D changeYZ(Parcels3D k){
        Parcels3D newParcel = new Parcels3D(k.getX(),k.getZ(),k.getY(),k.getValue());
        return newParcel;
    }

    /**
     * This method rotate a parcel changing
     * x axis with y axis
     * @param k a parcel for rotating 
     * @return the parcel rotated
     */
    public static Parcels3D changeXY(Parcels3D k){
        Parcels3D newParcel = new Parcels3D(k.getY(),k.getX(),k.getZ(),k.getValue());
        return newParcel;
    }

}

