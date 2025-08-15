package FinalFantasy.CombatSystem;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main implements ApplicationListener
{
    Bucket bucket = null;
    Drops drops;
    Lava lava;

    Texture backgroundTexture;

    Music music;

    SpriteBatch spriteBatch;
    FitViewport viewport;

    float delta = 0;

    float worldWidth;
    float worldHeight;


    @Override
    public void create()
    {
        backgroundTexture = new Texture("catito.png");

        music = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
        music.setLooping(true);
        music.setVolume(.5f);
        music.play();

        spriteBatch = new SpriteBatch();
        viewport = new FitViewport(8, 5);

        worldWidth = viewport.getWorldWidth();
        worldHeight = viewport.getWorldHeight();

        bucket = Bucket.create(viewport);
        drops =  Drops.create(viewport, bucket);
        lava =  Lava.create(viewport, bucket);

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
        bucket.ProcessInput();
        bucket.tick(delta);
        logic();
        draw();
    }


    private void logic()
    {
        drops.dropLogic(delta);

        lava.lavaLogic(delta);
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
        drops.dropMiddleDraw(spriteBatch);
        lava.lavaMiddleDraw(spriteBatch);
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
