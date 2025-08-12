package FinalFantasy.CombatSystem;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class Bucket
{
    Texture bucketTexture;
    Sprite bucketSprite;

    float bucketWidth;
    float bucketHeight;

    float minPos = 0;
    float maxPos = 0;

    float speed = 4f;

    Vector2 touchPos = new Vector2();

    FitViewport viewport;


    public void create(float worldWidth, FitViewport viewportProjection)
    {
        viewport = viewportProjection;

        bucketTexture = new Texture("bucket.png");

        bucketSprite = new Sprite(bucketTexture);
        bucketSprite.setSize(1,1);
        bucketWidth = bucketSprite.getWidth();
        bucketHeight = bucketSprite.getHeight();

        maxPos = worldWidth - bucketWidth;
    }


    public void input(float delta)
    {

        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT))
        {
            bucketSprite.translateX(speed * delta);
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
        {
            bucketSprite.translateX(-speed * delta);
        }

        if(Gdx.input.isTouched())
        {
            touchPos.set(Gdx.input.getX(),Gdx.input.getY());//get where the touch happened on screen
            viewport.unproject(touchPos);//convert units to world units of viewport
            bucketSprite.setCenterX(touchPos.x);//change the horizontal position of the bucket
        }

    }

    public void bucketLogic()
    {
        bucketSprite.setX(MathUtils.clamp(bucketSprite.getX(), minPos, maxPos));
    }

    public void render(SpriteBatch spriteBatch)
    {
        bucketSprite.draw(spriteBatch);
    }

    public Rectangle getPhysicalObject()
    {
        return bucketSprite.getBoundingRectangle();
    }
}
