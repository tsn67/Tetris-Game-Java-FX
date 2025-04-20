package Assets;


public class Tetrispiece {
    
    //class contains static pieces - matrix representation
    //Also their colors

    public static String[] colors = {"#16C79A", "#E94560"}; 
    public static int[][][] pieces = {
        {
            {0, 1, 1}, //L piece 
            {0, 1, 0},
            {0, 1, 0}
        },
        {
            {0, 0, 0}, //t piece
            {0, 1, 0},
            {1, 1, 1}
        }
    };

    //more colors will be added 
}
