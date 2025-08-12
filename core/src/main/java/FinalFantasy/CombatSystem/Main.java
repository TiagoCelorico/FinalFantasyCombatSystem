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

    Texture backgroundTexture;
    Texture dropTexture;
    Texture bucketTexture;
    Sprite bucketSprite;

    Sound dropSound;
    Music music;

    SpriteBatch spriteBatch;
    FitViewport viewport;

    Vector2 touchPos;

    Array<Sprite> dropSprites;
    float dropTimer;

    Rectangle bucketRectangle;
    Rectangle dropRectangle;

    float speed = 4f;
    float delta = Gdx.graphics.getDeltaTime();

    float worldWidth = viewport.getWorldWidth();
    float worldHeight = viewport.getWorldHeight();


    @Override
    public void create()
    {
        backgroundTexture = new Texture("catito.png");

        bucketTexture = new Texture("bucket.png");
        bucketSprite = new Sprite(bucketTexture);
        bucketSprite.setSize(1,1);

        dropTexture = new Texture("drop.png");

        dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.mp3"));
        music = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
        music.setLooping(true);
        music.setVolume(.5f);
        music.play();

        spriteBatch = new SpriteBatch();
        viewport = new FitViewport(8, 5);

        touchPos = new Vector2();

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
        input();
        logic();
        draw();
    }

    private void input()
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

    private void logic()
    {
        float bucketWidth = bucketSprite.getWidth();
        float bucketHeight = bucketSprite.getHeight();

        bucketSprite.setX(MathUtils.clamp(bucketSprite.getX(), 0, worldWidth - bucketWidth));

        float delta = Gdx.graphics.getDeltaTime();

        for(int i = dropSprites.size-1; i >=0; i--)
        {
            Sprite dropSprite = dropSprites.get(i);//get the sprites from the list

            float dropWidth = dropSprite.getWidth();
            float dropHeight = dropSprite.getHeight();

            dropSprite.translateY(-2f * delta);

            dropRectangle.set(dropSprite.getX(), dropSprite.getY(), dropWidth, dropHeight);

            if(dropSprite.getY() < -dropWidth)
            {
                dropSprites.removeIndex(i);
            }
            else if (bucketRectangle.overlaps(dropRectangle)) //check if bucket overlaps drop
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
        bucketSprite.draw(spriteBatch);

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
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose()
    {

    }
}
