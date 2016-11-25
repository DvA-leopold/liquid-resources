package com.liquidresources.game.model;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.ai.utils.Location;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Timer;
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

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


final public class EntityInitializer {
    EntityInitializer(final SceneLoader sceneLoader) {
        this.sceneLoader = sceneLoader;
        UpdatableBodyImpl.setEntityInitializer(this);
        timer = new Timer();

        baseEntitiesMap = new HashMap<>();
        baseEntitiesMap.put("capital", new Capital(RelationTypes.ALLY));
        baseEntitiesMap.put("pump_1", new Pump(RelationTypes.ALLY, BodyTypes.WATER_PUMP));
        baseEntitiesMap.put("pump_2", new Pump(RelationTypes.ALLY, BodyTypes.OIL_PUMP));
        baseEntitiesMap.put("pump_3", new Pump(RelationTypes.ALLY, BodyTypes.OIL_PUMP));
        baseEntitiesMap.put("factory", new PowerFactory(RelationTypes.ALLY));
        baseEntitiesMap.put("ion_shield", new IonShield(RelationTypes.ALLY));
        baseEntitiesMap.put("planet", new Planet(RelationTypes.NEUTRAL));

        ItemWrapper itemRoot = new ItemWrapper(sceneLoader.getRoot());
        for (Map.Entry<String, IScript> entry : baseEntitiesMap.entrySet()) {
            itemRoot.getChild(entry.getKey()).addScript(entry.getValue());
        }

        dynamicEntities = new HashMap<>();
        dynamicEntities.put(RelationTypes.ENEMY, new ArrayList<IScript>());
        dynamicEntities.put(RelationTypes.ALLY, new ArrayList<IScript>());
    }

    public void createEntityFromLibrary(String entityName,
                                        Class<? extends IScript> iScriptClass,
                                        float x,
                                        float y,
                                        RelationTypes entityRelation) { // TODO add synchronisation
        try {
            CompositeItemVO createdEntityData = sceneLoader.loadVoFromLibrary(entityName);
            Entity createdEntity = sceneLoader.entityFactory.createEntity(sceneLoader.getRoot(), createdEntityData);

            TransformComponent transformComponent = ComponentRetriever.get(createdEntity, TransformComponent.class);
            transformComponent.x = x;
            transformComponent.y = y;

            sceneLoader.entityFactory.initAllChildren(sceneLoader.getEngine(), createdEntity, createdEntityData.composite);
            sceneLoader.getEngine().addEntity(createdEntity);

            IScript script = iScriptClass.getDeclaredConstructor(RelationTypes.class).newInstance(entityRelation);
            new ItemWrapper(createdEntity).addScript(script);

            synchronized (dynamicEntities) {
                dynamicEntities.get(entityRelation).add(script);
            }
        } catch (NoSuchMethodException | InstantiationException | InvocationTargetException | IllegalAccessException err) {
            System.err.println(err.getMessage());
        }
    }

    /**
     * create entity in time intervals, need to start timer before create any entity
     * @param entityName entity name from the library
     * @param iScriptClass script class which instance will be attached to the library
     * @param secondDelay delay in seconds before first spawn
     * @param secondInterval spawn interval
     * @param repeatCount repeat count
     */
    void createEntityFromLibraryByTimer(final String entityName,
                                        final Class<? extends IScript> iScriptClass,
                                        final RelationTypes entityRelation,
                                        float secondDelay,
                                        float secondInterval,
                                        int repeatCount) {
        Timer.Task task = new Timer.Task() {
            @Override
            public void run() {
                int randomX = MathUtils.random(0, 35);
                int randomY = MathUtils.random(18, 22);
                createEntityFromLibrary(entityName, iScriptClass, randomX, randomY, entityRelation);
            }
        };
        timer.scheduleTask(task, secondDelay, secondInterval, repeatCount);
    }


    public Location<Vector2> getTargetEntity(RelationTypes relationType) {
        synchronized (dynamicEntities) {
            if (!dynamicEntities.get(relationType).isEmpty()) {
//                Vector2 pos = ((UpdatableBodyImpl) dynamicEntities.get(relationType).get(0)).getPosition();
//                System.out.println("target pos : " + pos.x + " " + pos.y);
                return (Location<Vector2>) dynamicEntities.get(relationType).get(0); // TODO fix warning with cast
            }
        }
        return null;
    }

    public SceneLoader getSceneLoader() {
        return sceneLoader;
    }

    public IScript getBaseSceneElement(String entityName) {
        return baseEntitiesMap.get(entityName);
    }

    void stopTimer() {
        timer.stop();
    }

    void startTimer() {
        timer.start();
    }

    void dispose() {
        timer.stop();
        timer.clear();
    }


    final private HashMap<String, IScript> baseEntitiesMap;
    final private HashMap<RelationTypes, ArrayList<IScript>> dynamicEntities;
    final private SceneLoader sceneLoader;
    final private Timer timer;
}
