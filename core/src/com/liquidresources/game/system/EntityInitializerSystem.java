package com.liquidresources.game.system;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.badlogic.gdx.physics.box2d.Body;
import com.kotcrab.vis.runtime.component.PhysicsBody;
import com.kotcrab.vis.runtime.component.VisSprite;
import com.kotcrab.vis.runtime.system.VisIDManager;
import com.kotcrab.vis.runtime.util.AfterSceneInit;
import com.liquidresources.game.model.bodies.*;
import com.liquidresources.game.model.bodies.bariers.IonShield;
import com.liquidresources.game.model.bodies.bariers.Planet;
import com.liquidresources.game.model.bodies.buildings.Capital;
import com.liquidresources.game.model.bodies.buildings.PowerFactory;
import com.liquidresources.game.model.bodies.buildings.Pump;
import com.liquidresources.game.model.types.BodyTypes;
import com.liquidresources.game.model.types.RelationTypes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


final public class EntityInitializerSystem implements AfterSceneInit {
    public EntityInitializerSystem() {
        UpdatableBody.setEntityInitializerSystem(this);

        staticEntities = new HashMap<>();
        staticEntities.put("capital", new Capital(RelationTypes.ALLY));
        staticEntities.put("pump_1", new Pump(RelationTypes.ALLY, BodyTypes.WATER_PUMP));
        staticEntities.put("pump_2", new Pump(RelationTypes.ALLY, BodyTypes.OIL_PUMP));
        staticEntities.put("pump_3", new Pump(RelationTypes.ALLY, BodyTypes.OIL_PUMP));
        staticEntities.put("factory", new PowerFactory(RelationTypes.ALLY));
        staticEntities.put("ion_shield", new IonShield(RelationTypes.ALLY));
        staticEntities.put("planet", new Planet(RelationTypes.NEUTRAL));

        dynamicEntities = new HashMap<>();
        dynamicEntities.put(RelationTypes.ENEMY, new ArrayList<UpdatableBody>());
        dynamicEntities.put(RelationTypes.ALLY, new ArrayList<UpdatableBody>());
        dynamicEntities.put(RelationTypes.NEUTRAL, new ArrayList<UpdatableBody>());
    }

    public void createEntityFromLibrary(String entityName, UpdatableBody iUpdatableBody, float x, float y) {

    }

    public UpdatableBody getTargetBody(RelationTypes relationType) {
        UpdatableBody closestBody = null;
        if (!dynamicEntities.get(relationType).isEmpty()) {
            UpdatableBody capitalBody = staticEntities.get("capital");
            for (UpdatableBody body: dynamicEntities.get(relationType)) {
                if (body.isInitialized() && body.getHunterUpdatableBody() == null) {
                    if (closestBody == null) {
                        closestBody = body;
                    } else {
                        float currentDst = body.getPosition().dst(capitalBody.getPosition());
                        float oldDst = closestBody.getPosition().dst(capitalBody.getPosition());
                        closestBody = (currentDst < oldDst) ? body : closestBody;
                    }
                }
            }
        }
        return closestBody;
    }

    public UpdatableBody getBaseSceneElement(String elementName) {
        return staticEntities.get(elementName);
    }

    public boolean hasTargetBodies(RelationTypes relationType, BodyTypes targetBodyType) {
        for (UpdatableBody body: dynamicEntities.get(relationType)) {
            if (body.getBodyType() == targetBodyType && body.getHunterUpdatableBody() == null) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void afterSceneInit() {
        for (Map.Entry<String, UpdatableBody> entry: staticEntities.entrySet()) {
            Entity player = idManager.get(entry.getKey());
            Body body = physicsCm.get(player).body;
            body.setUserData(entry.getValue());
//            sprite = spriteCm.get(player);
            entry.getValue().initialize(physicsCm.get(player).body);
        }
    }


    // should be initialized by artemis --
    private ComponentMapper<VisSprite> spriteCm;
    private ComponentMapper<PhysicsBody> physicsCm;
    private VisIDManager idManager;
    // -- -- -- -- -- -- -- -- -- -- -- --

    final private HashMap<String, UpdatableBody> staticEntities;

    final private HashMap<RelationTypes, ArrayList<UpdatableBody>> dynamicEntities;
}
