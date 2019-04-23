package FinalQuest;

import java.util.concurrent.ThreadLocalRandom;
import java.util.ArrayList;
import java.util.List;

/**
 * Level 1's boss. he flies up and down, speeding up as he takes damage At first
 * he fires a single laser, then eventually begins firing a triple shot attempts
 * to crash into the player after some damage a trio of Alien1's come through to
 * help from time to time
 *
 * @author Marco Tacca
 */
public class Boss1 extends Alien {

    private int max_health;
    private String attack_mode = "entry";
    private String fire_mode = "regular";
    private int direction = 1;
    private int step = 0;
    private int delay;
    private int reinforce;
    private final List<Sprite> reinforcement_list = new ArrayList<>();
    private int count = 0;
    private String status;
    private Explosion boom;

    /**
     * Constructor
     *
     * @param x starting x coordinate for the alien
     * @param y starting y coordinate for the alien
     * @param D is the difficulty of the alien: normal, hard, unforgiving
     */
    public Boss1(int x, int y, String D) {
        super(x, y, D);
        POINTS = 3000;
        this.boom = null;
        this.status = "good";
        this.sprite_type = "boss";
        DIFFICULTY = D;
        move_speed = 4;
        initAlien();
    }

    /**
     * Constructor, to adjust the speed, if needed
     *
     * @param x starting x coordinate for the alien
     * @param y starting y coordinate for the alien
     * @param D is the difficulty of the alien: normal, hard, unforgiving
     * @param s an integer value for how quickly the alien moves, adjusted for
     * difficulty. default is 4
     */
    public Boss1(int x, int y, String D, int s) {
        super(x, y, D);
        POINTS = 3000;
        this.boom = null;
        this.status = "good";
        this.sprite_type = "boss";
        DIFFICULTY = D;
        move_speed = s;
        initAlien();
    }

    /**
     * Init our alien by assigning it an image and getting it's dimensions
     */
    private void initAlien() {
        switch (DIFFICULTY) {
            case "normal":
                fire_rate = 75;  //how often lasers are fired
                health = 50; // how many times they can be hit before dying
                move_speed += 0; // how fast the alien moves
                missile_speed = (move_speed + 3) * -1; // how fast their lasers move
                delay = 180; // how long it takes for the boss to charge
                reinforce = 400;
                break;
            case "hard":
                fire_rate = 30;  //how often lasers are fired
                health = 100; // how many times they can be hit before dying
                move_speed += 2; // how fast the alien moves
                missile_speed = (move_speed + 3) * -1; // how fast their lasers move
                delay = 120;
                reinforce = 200;
                break;
            case "unforgiving":
                fire_rate = 10;  //how often lasers are fired
                health = 150; // how many times they can be hit before dying
                move_speed += 4; // how fast the alien moves
                missile_speed = (move_speed + 6) * -1; // how fast their lasers move
                delay = 60;
                reinforce = 100;
                break;
        }
        max_health = health;
        fire_count = ThreadLocalRandom.current().nextInt(fire_rate - 100, fire_rate + 1);
        missiles = new ArrayList<>();
        loadImage("src/resources/Boss1.png");
        getImageDimensions();
    }

    /**
     * This damages the alien and then returns its current health if health <= 0
     * it should be destroyed otherwise it makes a sound and takes some damage
     * @return the alien's current health
     */
    @Override
    public int damage() {
        if (attack_mode != "crash" && attack_mode != "entry") {
            health -= 1;
        }
        if (health > 0) {
            SoundEffect.ALIEN_HIT.play();
        } else {
            status = "exploding";

        }
        return health;
    }

    /**
     * Create a missile when activated. If the boss is sufficiently damaged
     * it will fire multiple missiles
     */
    @Override
    public void fire() {
        switch (fire_mode) {
            case "regular":
                missiles.add(new Missile(x - width + 80, y - 2 + height / 2, missile_speed, 0));
                switch (DIFFICULTY) {
                    case "hard":
                        missiles.add(new Missile(x - width + 80, y - 2 + height / 2, missile_speed, -1));
                        missiles.add(new Missile(x - width + 80, y - 2 + height / 2, missile_speed, 1));
                        break;
                    case "unforgiving":
                        missiles.add(new Missile(x - width + 80, y - 2 + height / 2, missile_speed, -4));
                        missiles.add(new Missile(x - width + 80, y - 2 + height / 2, missile_speed, 4));
                        missiles.add(new Missile(x - width + 80, y - 2 + height / 2, missile_speed, -2));
                        missiles.add(new Missile(x - width + 80, y - 2 + height / 2, missile_speed, 2));
                        break;
                }
                break;
            case "double time":
                if (DIFFICULTY == "normal") {
                    missiles.add(new Missile(x - width + 80, y - 2 + height / 2, missile_speed, 0));
                    missiles.add(new Missile(x - width + 80, y - 2 + height / 2, missile_speed, -1));
                    missiles.add(new Missile(x - width + 80, y - 2 + height / 2, missile_speed, 1));
                } else if (DIFFICULTY == "hard") {
                    missiles.add(new Missile(x - width + 80, y - 2 + height / 2, missile_speed, 0));
                    missiles.add(new Missile(x - width + 80, y - 2 + height / 2, missile_speed, -1));
                    missiles.add(new Missile(x - width + 80, y - 2 + height / 2, missile_speed, 1));
                    missiles.add(new Missile(x - width + 80, y - 2 + height / 2, missile_speed, -2));
                    missiles.add(new Missile(x - width + 80, y - 2 + height / 2, missile_speed, 2));
                } else if (DIFFICULTY == "unforgiving") {
                    missiles.add(new Missile(x - width + 80, y - 2 + height / 2, missile_speed, -4));
                    missiles.add(new Missile(x - width + 80, y - 2 + height / 2, missile_speed, 4));
                    missiles.add(new Missile(x - width + 80, y - 2 + height / 2, missile_speed, -2));
                    missiles.add(new Missile(x - width + 80, y - 2 + height / 2, missile_speed, 2));
                    missiles.add(new Missile(x - width + 80, y - 2 + height / 2, missile_speed, -6));
                    missiles.add(new Missile(x - width + 80, y - 2 + height / 2, missile_speed, 6));
                }
                break;
        }
    }

    /**
     * The boss first appears on the screen and moves up and down while firing 
     * a single laser blast. Once it has lost 1/3 life it begins to move faster
     * and fires 3 shots. After it has lost 2/3 life it moves even faster and 
     * randomly crashes into the player. It tries to crash into the player at 
     * each damage interval.
     */
    @Override
    public void move() {
        count++;
        switch (attack_mode) {
            case "entry":
                entry();
                break;
            case "slow fire sweep":
                slowFireSweep();
                break;
            case "med fire sweep":
                medFireSweep();
                break;
            case "fast fire sweep":
                fastFireSweep();
                break;
            case "crash":
                crash();
                break;
            case "blowup":
                blowup();
                break;
        }
        // this is used to send out enemy ships as backup
        if (count % reinforce == 0) {
            update_reinforcements();
        }
    }

    /**
     * The boss enters the screen
     */
    public void entry() {
        x -= move_speed;
        if (x <= 950) {

            if (health <= max_health / 3) {
                attack_mode = "fast fire sweep";
                if (direction < 0) {
                    direction = -3;
                } else {
                    direction = 3;
                }
            } else if (health <= max_health * 2 / 3) {
                attack_mode = "med fire sweep";
                if (direction < 0) {
                    direction = -2;
                } else {
                    direction = 2;
                }
            } else {
                attack_mode = "slow fire sweep";
            }
        }
    }

    /**
     * The boss moves up and down, firing it's missiles
     */
    public void slowFireSweep() {
        fire_count += 1;
        if (fire_count % fire_rate == 0) {
            fire();
        }
        y += move_speed * direction;
        if (y < 50) {
            direction = 1;
        }
        if (y > 750) {
            direction = -1;
        }
        if (health <= max_health / 3 * 2) {
            attack_mode = "crash";
            fire_mode = "double time";
            fire_rate = fire_rate / 3 * 2;
        }
    }

    /**
     * boss zooms across the screen, attempting to crash into the player
     */
    public void crash() {
        step++;
        if (step <= delay) {
            if (step % 3 == 0) {
                y += 5 * direction;
                direction = direction * -1;
            }
        } else if (x > 0 - width) {
            x -= move_speed * 4;
        } else {
            x = 1300;
            attack_mode = "entry";
            step = 0;
        }
    }

    /**
     * The boss moves up and down, firing it's missiles a little faster
     */
    public void medFireSweep() {
        fire_count += 1;
        if (fire_count % fire_rate == 0) {
            fire();
        }
        y += move_speed * direction;
        if (y < 50) {
            direction = 2;
        } else if (y > 750) {
            direction = -2;
        }
        if (health <= max_health / 3) {
            attack_mode = "crash";
            fire_rate = fire_rate / 2;
            delay = delay / 2;
        }
    }

    /**
     * The boss moves up and down, firing it's missiles a lot faster
     */
    public void fastFireSweep() {
        fire_count += 1;
        if (fire_count % fire_rate == 0) {
            fire();
        }
        y += move_speed * direction;
        if (y < 50) {
            direction = 3;
        }
        if (y > 750) {
            direction = -3;
        }
        if (count % reinforce / 3 * 2 == 0) {
            attack_mode = "crash";
        }
    }

    /**
     * Explosion animation for the boss
     */
    private void blowup() {
        step++;
        if (step <= 300) {
            if (step % 3 == 0) {
                y += 5 * direction;
                direction = direction * -1;
            }
            if (step % 5 == 0) {

                makeBoom();
            }
        } else {
            visible = false;
        }
    }

    /**
     * generates little explosions all over the boss when it dies
     */
    private void makeBoom() {
        SoundEffect.ALIEN_EXPLODE.play();
        int y_pos = ThreadLocalRandom.current().nextInt(y, y + height - 32);
        int x_pos = ThreadLocalRandom.current().nextInt(x, x + width - 32);
        boom = new Explosion(x_pos, y_pos);
    }

    /**
     * lets board know that the boss is exploding for purposes of status
     * change. Returns an explosion to draw if it is exploding, null otherwise
     * @return null or an explosion
     */
    @Override
    public Explosion getBoom() {
        if (boom == null) {
            return null;
        } else {
            Explosion tmp = boom;
            boom = null;
            return tmp;
        }
    }

    /**
     * Aliens are randomly generated to act as backup for the boss
     */
    public void update_reinforcements() {
        switch (DIFFICULTY) {
            case "normal": {
                int ypos = ThreadLocalRandom.current().nextInt(50, 890);
                reinforcement_list.add(new Alien1(1300, ypos, DIFFICULTY, 10));
                reinforcement_list.add(new Alien1(1400, ypos, DIFFICULTY, 10));
                reinforcement_list.add(new Alien1(1500, ypos, DIFFICULTY, 10));
                break;
            }
            case "hard": {
                int ypos = ThreadLocalRandom.current().nextInt(50, 420);
                reinforcement_list.add(new Alien1(1300, ypos, DIFFICULTY, 10));
                reinforcement_list.add(new Alien1(1400, ypos, DIFFICULTY, 10));
                reinforcement_list.add(new Alien1(1500, ypos, DIFFICULTY, 10));
                ypos = ThreadLocalRandom.current().nextInt(470, 890);
                reinforcement_list.add(new Alien1(1300, ypos, DIFFICULTY, 10));
                reinforcement_list.add(new Alien1(1400, ypos, DIFFICULTY, 10));
                reinforcement_list.add(new Alien1(1500, ypos, DIFFICULTY, 10));
                break;
            }
            case "unforgiving": {
                int ypos = ThreadLocalRandom.current().nextInt(50, 280);
                reinforcement_list.add(new Alien1(1300, ypos, DIFFICULTY, 10));
                reinforcement_list.add(new Alien1(1400, ypos, DIFFICULTY, 10));
                reinforcement_list.add(new Alien1(1500, ypos, DIFFICULTY, 10));
                ypos = ThreadLocalRandom.current().nextInt(330, 600);
                reinforcement_list.add(new Alien1(1300, ypos, DIFFICULTY, 10));
                reinforcement_list.add(new Alien1(1400, ypos, DIFFICULTY, 10));
                reinforcement_list.add(new Alien1(1500, ypos, DIFFICULTY, 10));
                ypos = ThreadLocalRandom.current().nextInt(650, 890);
                reinforcement_list.add(new Alien1(1300, ypos, DIFFICULTY, 10));
                reinforcement_list.add(new Alien1(1400, ypos, DIFFICULTY, 10));
                reinforcement_list.add(new Alien1(1500, ypos, DIFFICULTY, 10));
                break;
            }
        }
    }

    /**
     * When aliens have been generated, this lets Board know they are ready to go
     * returns an alien if there is an alien, otherwise it returns null
     * @return 
     */
    @Override
    public Sprite checkReinforcements() {
        if (reinforcement_list.size() <= 0) {
            return null;
        } else {
            return reinforcement_list.remove(0);
        }
    }

    /**
     * sets status to exploding
     */
    @Override
    public void explode() {
        status = "gone";
        attack_mode = "blowup";
        step = 0;
    }

    /**
     * mostly used to make things explode
     *
     * @return the current status of the sprite
     */
    @Override
    public String getStatus() {
        return status;
    }

}
