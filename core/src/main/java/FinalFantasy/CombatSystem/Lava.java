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

public class Lava
{
    Bucket bucket;

    Texture lavaTexture;

    Array<Sprite> lavaSprites;

    float lavaTimer;

    float worldWidth;
    float worldHeight;

    float lavaWidth = .25f;
    float lavaHeight = 1;

    FitViewport viewport;

    private Lava (FitViewport viewportProjection, Bucket bucketReference)
    {
        viewport = viewportProjection;

        lavaTexture = new Texture("lava.png");

        lavaSprites = new Array<>();

        this.worldWidth = viewport.getWorldWidth();
        worldHeight = viewport.getWorldHeight();

        bucket = bucketReference;
    }

    public static Lava create(FitViewport viewportProjection, Bucket bucketReference)
    {
        return new Lava(viewportProjection, bucketReference);
    }

    public void lavaLogic(float delta)
    {
        for(int i = lavaSprites.size-1; i >=0; i--)
        {
            Sprite lavaSprite = lavaSprites.get(i);//get the sprites from the list
            Rectangle lavaRectangle = lavaSprite.getBoundingRectangle();


            float lavaHeight = lavaSprite.getHeight();


            lavaSprite.translateY(-2f * delta);



            if(lavaSprite.getY() < -lavaHeight)
            {
                lavaSprites.removeIndex(i);
            }
            else if (bucket.getPhysicalObject().overlaps(lavaRectangle)) //check if bucket overlaps lava
            {
                lavaSprites.removeIndex(i); //remove lava

            }
        }

        lavaTimer += delta;
        if(lavaTimer > 1f)
        {
            lavaTimer = 0;
            createLava();
        }
    }

    private void createLava()
    {
        Sprite lavaSprite = new Sprite(lavaTexture);

        lavaSprite.setSize(lavaWidth,lavaHeight);
        lavaSprite.setX(MathUtils.random(0f, worldWidth - lavaWidth)); //random Lava position
        lavaSprite.setY(worldHeight);
        lavaSprites.add(lavaSprite);
    }

    public void lavaMiddleDraw(SpriteBatch spriteBatch)
    {
        for(Sprite lavaSprite : lavaSprites)
        {
            lavaSprite.draw(spriteBatch);
        }
    }





















}
