package core;

import java.io.DataInputStream;
import java.io.IOException;

public class Segment {
    private float deltaLong;
    private float deltaLat;

    public Segment(DataInputStream dis) throws IOException {
        this.deltaLong = (dis.readShort()) / 2.0E5f ;
        this.deltaLat = (dis.readShort()) / 2.0E5f ;
    }

    public Segment(float delaLong, float deltaLat){
        this.deltaLong = delaLong;
        this.deltaLat = deltaLat;
    }
}
