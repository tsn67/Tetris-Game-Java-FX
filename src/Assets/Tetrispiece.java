package Assets;

public class Tetrispiece {

        // class contains static pieces - matrix representation
        // Also their colors

        public static String[] colors = { "#00FFFF",
                        "#0000FF",
                        "#FFA500",
                        "#FFFF00",
                        "#00FF00",
                        "#800080",
                        "#FF0000"
        };

        public static int[][][] pieces = {
                        {
                                        { 0, 1, 1 }, // L piece
                                        { 0, 1, 0 },
                                        { 0, 1, 0 }
                        },
                        {

                                        { 1, 1, 0 }, // L piece
                                        { 0, 1, 0 },
                                        { 0, 1, 0 }
                        },
                        {
                                        { 0, 1, 0 }, // t piece
                                        { 1, 1, 1 },
                                        { 0, 0, 0 }
                        },
                        {
                                        { 0, 1, 0 }, { 0, 1, 0 }, { 0, 1, 0 } // I piece
                        },
                        {
                                        { 0, 0, 0, 0 }, { 0, 1, 1, 0 }, { 0, 1, 1, 0 }, { 0, 0, 0, 0 }
                        }

        };

        // more colors will be added
}
