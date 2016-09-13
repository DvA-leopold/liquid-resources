package com.liquidresources.game.model;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Timer;
import com.liquidresources.game.model.bodies.UpdatableBodyImpl;
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
import java.util.HashMap;
import java.util.Map;


final public class EntityInitializer {
    EntityInitializer(final SceneLoader sceneLoader) {
        this.sceneLoader = sceneLoader;
        UpdatableBodyImpl.setEntityInitializer(this);
        timer = new Timer();

        entityMap = new HashMap<>();
        entityMap.put("capital", new Capital(RelationTypes.ALLY));
        entityMap.put("pump_1", new Pump(RelationTypes.ALLY, BodyTypes.WATER_PUMP));
        entityMap.put("pump_2", new Pump(RelationTypes.ALLY, BodyTypes.OIL_PUMP));
        entityMap.put("pump_3", new Pump(RelationTypes.ALLY, BodyTypes.OIL_PUMP));
        entityMap.put("factory", new PowerFactory(RelationTypes.ALLY));
        entityMap.put("ion_shield", new IonShield(RelationTypes.ALLY));
        entityMap.put("planet", new Planet(RelationTypes.NEUTRAL));

        ItemWrapper itemRoot = new ItemWrapper(sceneLoader.getRoot());
        for (Map.Entry<String, IScript> entry : entityMap.entrySet()) {
            itemRoot.getChild(entry.getKey()).addScript(entry.getValue());
        }
    }

    private void createEntityFromLibrary(String entityName,
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
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException err) {
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

    public Engine getEngine() {
        return sceneLoader.getEngine();
    }

    public IScript getSceneElement(String entityName) {
        return entityMap.get(entityName);
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


    final private SceneLoader sceneLoader;
    final private HashMap<String, IScript> entityMap;
    final private Timer timer;
}
