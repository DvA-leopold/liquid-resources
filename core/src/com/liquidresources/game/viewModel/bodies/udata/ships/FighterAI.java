package com.liquidresources.game.viewModel.bodies.udata.ships;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.liquidresources.game.model.BodyFactoryWrapper;
import com.liquidresources.game.model.UpdatableBody;
import com.liquidresources.game.model.types.BodyTypes;
import com.liquidresources.game.model.types.RelationTypes;
import com.liquidresources.game.viewModel.bodies.udata.SteerableBodyImpl;

import static com.liquidresources.game.model.common.utils.UConverter.p2m;

class FighterAI {
    FighterAI(RelationTypes shipOwnerBase) {
        this.shipOwnerBase = shipOwnerBase;
        fighterStatus = FighterStatus.SEARCH;

        shootRange = 300f; // TODO dynamic range
        shortShootRange = 50f;
        maxBulletCount = 5;
    }

    boolean allowAttack(final Vector2 myPosition) {
        try {
            float distance = p2m(myPosition.dst(target.getPosition()));
//            System.out.println("a.distance: " + distance);
//            System.out.println("t.pos: " + target.getPosition().x + " " + target.getPosition().y);
            if (shootRange > distance) {
                if (bulletCount <= 0) {
                    fighterStatus = FighterStatus.RELOAD;
                } else {
                    return true;
                }
            } else {
                fighterStatus = FighterStatus.GET_CLOSE;
            }
        }
        catch(NullPointerException e) {
            System.out.println("no target or position");
        }

        return false;
    }

    void retarget(Vector2 myPosition, final BodyFactoryWrapper bodyFactoryWrapper) {
        for (Body ship : bodyFactoryWrapper.getShipsBodies()) {
            if (((UpdatableBody) ship.getUserData()).getRelation() != shipOwnerBase) {
                Vector2 currentEnemyPosition = ((UpdatableBody) ship.getUserData()).getPosition();

                if (target == null || myPosition.dst(currentEnemyPosition) < myPosition.dst(target.getPosition())) {
                    myPosition = currentEnemyPosition;
                    target = (SteerableBodyImpl) ship.getUserData();
                }
            }
        }
        if (target == null) {
            for (Body building: bodyFactoryWrapper.getBuildingsBodies()) {
                BodyTypes currentType = ((UpdatableBody) building.getUserData()).getBodyType();
                if (currentType != BodyTypes.GROUND && currentType != BodyTypes.ION_SHIELD &&
                        ((UpdatableBody) building.getUserData()).getRelation() != shipOwnerBase) {
                    target = (SteerableBodyImpl) building.getUserData();
                    break;
                }
            }
        }

        fighterStatus = FighterStatus.ATTACK;
    }

    void reload(float delta) {
        final float reloadIntervalRate = 0.5f;
        if (bulletCount >= maxBulletCount) {
            fighterStatus = FighterStatus.ATTACK;
            return;
        }
        if (reloadIntervalRate < currentReloadRate) {
            bulletCount++;
            currentReloadRate = 0f;
        } else {
            currentReloadRate += delta;
        }
        System.out.println("bullets: " + bulletCount);
    }

    void getClose(Vector2 myPosition) {
        float distance = p2m(myPosition.dst(target.getPosition()));
//        System.out.println("get close: " + distance + " " + shootRange);
        if (shootRange >= distance) {
            fighterStatus = FighterStatus.ATTACK;
        }
    }

    boolean allowShoot(float delta) {
        final float shootIntervalRate = 0.05f;
        if (shootIntervalRate < currentShootRate) {
            currentShootRate = 0;
            bulletCount--;
            return true;
        } else {
            currentShootRate += delta;
            return false;
        }
    }

    FighterStatus getFighterStatus() {
        return fighterStatus;
    }

    SteerableBodyImpl getTarget() {
        return target;
    }

    RelationTypes getRelationType() {
        return shipOwnerBase;
    }

    enum FighterStatus {
        SEARCH,
        ATTACK,
        RELOAD,
        GO_AWAY,
        GET_CLOSE
    }


    private SteerableBodyImpl target = null;

    private RelationTypes shipOwnerBase;
    private FighterStatus fighterStatus;

    private float shootRange;
    private float shortShootRange;
    private float currentReloadRate;
    private float currentShootRate;

    private short bulletCount;
    private short maxBulletCount;
}
