package com.liquidresources.game.model;

import com.badlogic.ashley.core.Entity;
import com.liquidresources.game.model.bodies.*;
import com.liquidresources.game.model.bodies.bariers.IonShield;
import com.liquidresources.game.model.bodies.bariers.Planet;
import com.liquidresources.game.model.bodies.buildings.Capital;
import com.liquidresources.game.model.bodies.buildings.PowerFactory;
import com.liquidresources.game.model.bodies.buildings.Pump;
import com.liquidresources.game.model.types.BodyTypes;
import com.liquidresources.game.model.types.RelationTypes;
import com.uwsoft.editor.renderer.SceneLoader;
import com.uwsoft.editor.renderer.components.TransformComponent;
import com.uwsoft.editor.renderer.data.CompositeItemVO;
import com.uwsoft.editor.renderer.scripts.IScript;
import com.uwsoft.editor.renderer.utils.ComponentRetriever;
import com.uwsoft.editor.renderer.utils.ItemWrapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


final public class EntityInitializer {
    EntityInitializer(final SceneLoader sceneLoader) {
        this.sceneLoader = sceneLoader;
        UpdatableBody.setEntityInitializer(this);

        staticEntities = new HashMap<>();
        staticEntities.put("capital", new Capital(RelationTypes.ALLY));
        staticEntities.put("pump_1", new Pump(RelationTypes.ALLY, BodyTypes.WATER_PUMP) {
            @Override
            public void act(float delta) {
                if (health > 0) {
                    ((Capital) staticEntities.get("capital")).changeWater(1);
                }
            }
        });
        staticEntities.put("pump_2", new Pump(RelationTypes.ALLY, BodyTypes.OIL_PUMP) {
            @Override
            public void act(float delta) {
                if (health > 0) {
                    ((Capital) staticEntities.get("capital")).changeOil(1);
                }
            }
        });
        staticEntities.put("pump_3", new Pump(RelationTypes.ALLY, BodyTypes.OIL_PUMP) {
            @Override
            public void act(float delta) {
                if (health > 0) {
                    ((Capital) staticEntities.get("capital")).changeOil(1);
                }
            }
        });
        staticEntities.put("factory", new PowerFactory(RelationTypes.ALLY));
        staticEntities.put("ion_shield", new IonShield(RelationTypes.ALLY));
        staticEntities.put("planet", new Planet(RelationTypes.NEUTRAL));

        bodiesSheduledForInit = new ArrayList<>();

        ItemWrapper itemRoot = new ItemWrapper(sceneLoader.getRoot());
        for (Map.Entry<String, UpdatableBody> entry : staticEntities.entrySet()) {
            itemRoot.getChild(entry.getKey()).addScript(entry.getValue());
        }

        dynamicEntities = new HashMap<>();
        dynamicEntities.put(RelationTypes.ENEMY, new ArrayList<UpdatableBody>());
        dynamicEntities.put(RelationTypes.ALLY, new ArrayList<UpdatableBody>());
        dynamicEntities.put(RelationTypes.NEUTRAL, new ArrayList<UpdatableBody>());
    }

    public void createEntityFromLibrary(String entityName,
                                        UpdatableBody iUpdatableBody,
                                        float x,
                                        float y) {
        CompositeItemVO createdEntityData = sceneLoader.loadVoFromLibrary(entityName);
        Entity createdEntity = sceneLoader.entityFactory.createEntity(sceneLoader.getRoot(), createdEntityData);

        TransformComponent transformComponent = ComponentRetriever.get(createdEntity, TransformComponent.class);
        transformComponent.x = x;
        transformComponent.y = y;

        sceneLoader.entityFactory.initAllChildren(sceneLoader.getEngine(), createdEntity, createdEntityData.composite);
        sceneLoader.getEngine().addEntity(createdEntity);

        new ItemWrapper(createdEntity).addScript(iUpdatableBody);
        dynamicEntities.get(iUpdatableBody.getRelation()).add(iUpdatableBody);
    }

    public void destroyEntity(UpdatableBody bodyForRemove) {
        sceneLoader.getEngine().removeEntity(bodyForRemove.getEntity());
        dynamicEntities.get(bodyForRemove.getRelation()).remove(bodyForRemove);
    }

    void initSheduledBodies() {
        if (!bodiesSheduledForInit.isEmpty()) {
            for (UpdatableBody body: bodiesSheduledForInit) {
                body.init(null);
            }
            bodiesSheduledForInit.clear();
        }
    }

    public void sheduleForInitPhysicComponent(UpdatableBody bodyForShedule) {
        bodiesSheduledForInit.add(bodyForShedule);
    }

    public UpdatableBody getTargetBody(RelationTypes relationType) {
        UpdatableBody closestBody = null;
        if (!dynamicEntities.get(relationType).isEmpty()) {
            UpdatableBody capitalBody = staticEntities.get("capital");
            for (UpdatableBody body: dynamicEntities.get(relationType)) {
                if (body.getHunterUpdatableBody() == null) {
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

    public boolean hasTargetBodies(RelationTypes relationType, BodyTypes targetBodyType) {
        for (UpdatableBody body: dynamicEntities.get(relationType)) {
            if (body.getBodyType() == targetBodyType && body.getHunterUpdatableBody() == null) {
                return true;
            }
        }
        return false;
    }

    public IScript getBaseSceneElement(String entityName) {
        return staticEntities.get(entityName);
    }


    final private ArrayList<UpdatableBody> bodiesSheduledForInit;

    final private HashMap<String, UpdatableBody> staticEntities;
    final private HashMap<RelationTypes, ArrayList<UpdatableBody>> dynamicEntities;
    final private SceneLoader sceneLoader;
}
