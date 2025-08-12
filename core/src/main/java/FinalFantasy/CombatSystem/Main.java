package FinalFantasy.CombatSystem;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.Vector;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main implements ApplicationListener
{
    Bucket bucket;

    Texture backgroundTexture;
    Texture dropTexture;

    Sound dropSound;
    Music music;

    SpriteBatch spriteBatch;
    FitViewport viewport;

    Array<Sprite> dropSprites;
    float dropTimer;




    float delta = 0;

    float worldWidth;
    float worldHeight;


    @Override
    public void create()
    {
        backgroundTexture = new Texture("catito.png");

        dropTexture = new Texture("drop.png");

        dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.mp3"));
        music = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
        music.setLooping(true);
        music.setVolume(.5f);
        music.play();

        spriteBatch = new SpriteBatch();
        viewport = new FitViewport(8, 5);

        worldWidth = viewport.getWorldWidth();
        worldHeight = viewport.getWorldHeight();



        dropSprites = new Array<>();



    }

    @Override
    public void resize(int width, int height)
    {
        viewport.update(width, height, true);
    }

    @Override
    public void render()
    {
        delta = Gdx.graphics.getDeltaTime();
        bucket.input(delta);
        logic();
        draw();
    }


    private void logic()
    {

        bucket.bucketLogic();

        float delta = Gdx.graphics.getDeltaTime();

        for(int i = dropSprites.size-1; i >=0; i--)
        {
            Sprite dropSprite = dropSprites.get(i);//get the sprites from the list
            Rectangle dropRectangle = dropSprite.getBoundingRectangle();


            float dropWidth = dropSprite.getWidth();
            float dropHeight = dropSprite.getHeight();

            dropSprite.translateY(-2f * delta);

            //dropRectangle.set(dropSprite.getX(), dropSprite.getY(), dropWidth, dropHeight);

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

    private void draw()
    {
        ScreenUtils.clear(Color.BLACK);

        viewport.apply();

        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);

        spriteBatch.begin();//begin very important


        middleDraw();


        spriteBatch.end();//end very important
    }

    private void middleDraw()
    {
        spriteBatch.draw(backgroundTexture,0,0, worldWidth, worldHeight);
        bucket.render(spriteBatch);

        for(Sprite dropSprite : dropSprites)
        {
            dropSprite.draw(spriteBatch);
        }
    }

    private void createDroplet()
    {
        float dropWidth = 1;
        float dropHeight = 1;

        Sprite dropSprite = new Sprite(dropTexture);

        dropSprite.setSize(dropWidth,dropHeight);
        dropSprite.setX(MathUtils.random(0f,worldWidth - dropWidth)); //random drop position
        dropSprite.setY(worldHeight);
        dropSprites.add(dropSprite);
    }


    @Override
    public void pause()
    {

    }

    @Override
    public void resume()
    {

    }

    @Override
    public void dispose()
    {

    }
}
