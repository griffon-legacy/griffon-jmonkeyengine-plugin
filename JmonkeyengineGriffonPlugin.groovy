/*
 * Copyright 2009-2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the 'License');
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an 'AS IS' BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * @author Andres Almiray
 */
class JmonkeyengineGriffonPlugin {
    // the plugin version
    String version = "0.3"
    // the version or versions of Griffon the plugin is designed for
    String griffonVersion = '1.0.0 > *' 
    // the other plugins this plugin depends on
    Map dependsOn = [swing: '1.0.0', lwjgl: '0.5']
    // resources that are included in plugin packaging
    List pluginIncludes = []
    // the plugin license
    String license = 'Apache Software License 2.0'
    // Toolkit compatibility. No value means compatible with all
    // Valid values are: swing, javafx, swt, pivot, gtk
    List toolkits = ['swing']
    // Platform compatibility. No value means compatible with all
    // Valid values are:
    // linux, linux64, windows, windows64, macosx, macosx64, solaris
    List platforms = []
    String documentation = ''
    String source = 'https://github.com/griffon/griffon-jmonkeyengine-plugin'

    List authors = [
        [
            name: 'Andres Almiray',
            email: 'aalmiray@yahoo.com'
        ]
    ]
    String title = 'JMonkeyEngine support'
    def description = '''
Create 3D scenes and games with the power of [JMonkeyEngine][1] and Griffon combined.

Usage
-----

This plugin has two modes of operation: `normal` and `embedded`.

### Normal Mode

In this mode the first `application()` node to be resolved will produce a subclass of jME3's `com.jme3.app.SimpleApplication`. Subsequent should
produce the default container, typically a JFrame in a Swing application. This is the **default** mode of operation. When in this mode you can
define what the game does. The following example shows a rotating cube

__griffon-app/models/sample/SampleModel.groovy__

        package sample
        import com.jme3.scene.Geometry
        class SampleModel {
            Geometry cube
        }

__griffon-app/views/sample/SampleView.groovy__

        package sample
        import com.jme3.material.Material
        import com.jme3.math.ColorRGBA
        import com.jme3.math.Vector3f
        import com.jme3.math.Quaternion
        import com.jme3.renderer.RenderManager
        import com.jme3.scene.Geometry
        import com.jme3.scene.shape.Box
 
        application(title: 'JMonkeyEngine + Griffon',
          locationByPlatform:true,
          iconImage: imageIcon('/griffon-icon-48x48.png').image,
          iconImages: [imageIcon('/griffon-icon-48x48.png').image,
                       imageIcon('/griffon-icon-32x32.png').image,
                       imageIcon('/griffon-icon-16x16.png').image]) {
            app.game.onInit = {
                Box box = new Box(Vector3f.ZERO, 1, 1, 1)
                model.cube = new Geometry("Box", box)
                model.cube.updateModelBound()
 
                Material mat = new Material(assetManager, 'Common/MatDefs/Misc/Unshaded.j3md')
                mat.setTexture("m_ColorMap", assetManager.loadTexture("griffon-icon-256x256.png"))
                model.cube.material = mat
 
                rootNode.attachChild(model.cube)
            }
 
            app.game.onUpdate = { float tpf ->
                model.cube.rotate((-1.5*tpf) as float, (2*tpf) as float, tpf)
            }
        }

### Closures

The game subclass allows the following closure properties to be defined:

 *  **onInit()** - initializes the game, sames as `SimpleApplication.simpleInitApp()`.
 *  **onUpdate(float tpf)** - updates the game, same as `SimpleApplication.simpleUpdate(float tpf)`.
 *  **onRender(RenderManager rm)** - called when a render pass is made, same as `SimpleApplication.simpleRender(RenderManager rm)`.

### Events ###

The following events will be triggered by this mode

 *  **JmeInit[app, gc]** - triggered when the game inits itself
 *  **JmeUpdate[app, tpf]** - triggered when the game has been updated
 *  **JmeRender[app, rm]** - triggered when the game has completed a render pass (rm stands for `RenderManager`)

## Embedded mode ##

In this mode there will be no automatic game handling; every single `application()` node will resolve to the default container. Developers are
responsible for initializing jME3 and embed a View as you see fit. The following configuration flag enables this mode

__griffon-app/conf/BuildConfig.groovy__

        jmonkeyengine.embedded = true


[1]: http://www.jmonkeyengine.com/
'''
}

