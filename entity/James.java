package entity;
import java.awt.Graphics2D;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;


public class James extends Entity{
    GamePanel gp;

    public James(GamePanel gp){
        this.gp = gp;
        type = "Player";
        getJamesImage();
        loadJumpFrames();
        }

        private void loadJumpFrames() {
            // Load jump frames as before
        }
        public void getJamesImage(){
            try {
                downA = ImageIO.read(getClass().getResourceAsStream("/entity/james.png"));
                
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        public void draw(Graphics2D graphics2) {
            // graphics2.setColor(Color.white);
    
            // graphics2.fillRect(x, y , gp.tileSize, gp.tileSize);

                graphics2.drawImage(downA, 1000, 50, gp.tileSize * 3, gp.tileSize * 3, null);
            }

    @Override
    public void update() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
        
    
}
