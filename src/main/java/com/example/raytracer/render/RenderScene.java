package com.example.raytracer.render;
import com.example.raytracer.bvh.BVH;
import com.example.raytracer.helper.Intersection;
import com.example.raytracer.helper.LightSource;
import com.example.raytracer.helper.Ray;
import com.example.raytracer.geometry.SceneObject;

import java.util.ArrayList;

/**
 * Represents the scene to be rendered.
 * Stores the objects, light source and BVH
 */
public class RenderScene {

    private ArrayList<SceneObject> objects = new ArrayList<>();
    public LightSource light;

    private BVH bvh;

    public RenderScene(ArrayList<SceneObject> objects, LightSource light) {
        this.light = light;
        this.objects = new ArrayList<>(objects);
        bvh = new BVH(new ArrayList<>(objects));
    }

    /**
     * returns the closest intersection between the ray and the scene
     * @param ray the ray being traced
     * @return an Intersection containing information about the closest hit
     */
    public Intersection closestHit(Ray ray){
        return bvh.traverseBVH(ray);
    }

    public Intersection closestHitNaive(Ray ray){
        Intersection closest = new Intersection(null , Double.POSITIVE_INFINITY);
        for(SceneObject object : objects){
            Intersection hit = object.intersect(ray);
            if(hit != null && hit.t < closest.t ){
                closest = hit;
            }
        }
        return closest;
    }


    public void addObjects(ArrayList<SceneObject> newObjects){
        objects.addAll(newObjects);
        bvh.addObjects(newObjects);
        bvh.constructBVH();
    }

    public void clearObjects(){
        objects.clear();
        bvh.clearObjects();
    }

    public void setShininess(double shininess){
        for(SceneObject obj: objects){
            obj.setShininess(shininess);
        }
    }
}
