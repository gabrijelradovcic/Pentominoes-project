package databases;
public class parcelsABC{
    public int x;
    public int y;
    public int z;
    public int value;

    /**
     * This method creates parcels 
     * and the size is determinated by the
     * heigth, length and width.
     * It also stores the value of the parcel.
     * @param height
     * @param length
     * @param width
     * @param value
     */
    public parcelsABC(int height,int length, int width, int value){ 
        x = height;
        y = length;
        z = width;
        this.value = value;
    }

    /**
     * Getters of the class
     * @return instance variables.
     */

    public int getX(){ // returns size of parcel in x axis
        return x;
    }
    public int getY(){ // returns size of parcel in y axis
        return y;
    }
    public int getZ(){ // returns size of parcel in z axis
        return z;
    }
    public int getValue(){ // return value of parcel
        return value;
    }

    
    public String toString(){ // for printin parcel
        return x+"|"+y+"|"+z;
    }
    public parcelsABC clone(){ // for getting same object whith another reference
        return new parcelsABC(x,y,z,value);
    }
    public boolean equalParcel(parcelsABC k) { // for checking if parcels are same, used to remove duplicates
        if(k.getX()==x&&k.getY()==y&&k.getZ()==z){
            return true;
        }
        return false;
        
    }
}