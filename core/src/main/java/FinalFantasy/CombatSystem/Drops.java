package FinalFantasy.CombatSystem;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class Drops
{
    Bucket bucket;

    Texture dropTexture;

    Sound dropSound;

    Array<Sprite> dropSprites;

    float dropTimer;

    float worldWidth;
    float worldHeight;

    float dropWidth = 1;
    float dropHeight = 1;

    FitViewport viewport;

    public void create(float worldWidth, FitViewport viewportProjection)
    {
        viewport = viewportProjection;

        dropTexture = new Texture("drop.png");

        dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.mp3"));

        dropSprites = new Array<>();

        worldWidth = viewport.getWorldWidth();
        worldHeight = viewport.getWorldHeight();
    }


    public void dropLogic(float delta)
    {
        for(int i = dropSprites.size-1; i >=0; i--)
        {
            Sprite dropSprite = dropSprites.get(i);//get the sprites from the list
            Rectangle dropRectangle = dropSprite.getBoundingRectangle();


            float dropWidth = dropSprite.getWidth();


            dropSprite.translateY(-2f * delta);



            if(dropSprite.getY() < -dropWidth)
            {
                dropSprites.removeIndex(i);
            }
            else if (bucket.getPhysicalObject().overlaps(dropRectangle)) //check if bucket overlaps drop
            {
                dropSprites.removeIndex(i); //remove drop
                dropSound.play(); //play sound
            }
        }

        dropTimer += delta;
        if(dropTimer > 1f)
        {
            dropTimer = 0;
            createDroplet();
        }
    }


    public void dropMiddleDraw(SpriteBatch spriteBatch)
    {
        for(Sprite dropSprite : dropSprites)
        {
            dropSprite.draw(spriteBatch);
        }
    }


    private void createDroplet()
    {

        Sprite dropSprite = new Sprite(dropTexture);

        dropSprite.setSize(dropWidth,dropHeight);
        dropSprite.setX(MathUtils.random(0f, worldWidth - dropWidth)); //random drop position
        dropSprite.setY(worldHeight);
        dropSprites.add(dropSprite);
    }
















}
