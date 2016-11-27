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
        UpdatableBodyImpl.setEntityInitializer(this);

        staticEntities = new HashMap<>();
        staticEntities.put("capital", new Capital(RelationTypes.ALLY));
        staticEntities.put("pump_1", new Pump(RelationTypes.ALLY, BodyTypes.WATER_PUMP));
        staticEntities.put("pump_2", new Pump(RelationTypes.ALLY, BodyTypes.OIL_PUMP));
        staticEntities.put("pump_3", new Pump(RelationTypes.ALLY, BodyTypes.OIL_PUMP));
        staticEntities.put("factory", new PowerFactory(RelationTypes.ALLY));
        staticEntities.put("ion_shield", new IonShield(RelationTypes.ALLY));
        staticEntities.put("planet", new Planet(RelationTypes.NEUTRAL));

        bodiesSheduledForInit = new ArrayList<>();

        ItemWrapper itemRoot = new ItemWrapper(sceneLoader.getRoot());
        for (Map.Entry<String, UpdatableBodyImpl> entry : staticEntities.entrySet()) {
            itemRoot.getChild(entry.getKey()).addScript(entry.getValue());
        }

        dynamicEntities = new HashMap<>();
        dynamicEntities.put(RelationTypes.ENEMY, new ArrayList<UpdatableBodyImpl>());
        dynamicEntities.put(RelationTypes.ALLY, new ArrayList<UpdatableBodyImpl>());
        dynamicEntities.put(RelationTypes.NEUTRAL, new ArrayList<UpdatableBodyImpl>());
    }

    public void createEntityFromLibrary(String entityName,
                                        UpdatableBodyImpl iUpdatableBody,
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

    public void destroyEntity(RelationTypes relationType, UpdatableBodyImpl bodyForRemove) {
        sceneLoader.getEngine().removeEntity(bodyForRemove.getEntity());
        dynamicEntities.get(relationType).remove(bodyForRemove);
    }

    void initSheduledBodies() {
        if (!bodiesSheduledForInit.isEmpty()) {
            for (UpdatableBodyImpl body: bodiesSheduledForInit) {
                body.init(null);
            }
            bodiesSheduledForInit.clear();
        }
    }

    public void sheduleForInitPhysicComponent(UpdatableBodyImpl bodyForShedule) {
        bodiesSheduledForInit.add(bodyForShedule);
    }

    public UpdatableBodyImpl getTargetBody(RelationTypes relationType) {
        UpdatableBodyImpl closestBody = null;
        UpdatableBodyImpl capitalBody = staticEntities.get("capital");
        if (!dynamicEntities.get(relationType).isEmpty()) {
            for (UpdatableBodyImpl body: dynamicEntities.get(relationType)) {
                if (closestBody == null) {
                    closestBody = body;
                } else {
                    float currentDst = body.getPosition().dst(capitalBody.getPosition());
                    float oldDst = closestBody.getPosition().dst(capitalBody.getPosition());
                    closestBody = (currentDst < oldDst) ? body : closestBody;
                }
            }
        }
        System.out.println("size: " + dynamicEntities.get(RelationTypes.ENEMY).size() + " " + closestBody);
        return closestBody;
    }

    public IScript getBaseSceneElement(String entityName) {
        return staticEntities.get(entityName);
    }


    final private ArrayList<UpdatableBodyImpl> bodiesSheduledForInit;

    final private HashMap<String, UpdatableBodyImpl> staticEntities;
    final private HashMap<RelationTypes, ArrayList<UpdatableBodyImpl>> dynamicEntities;
    final private SceneLoader sceneLoader;
}
