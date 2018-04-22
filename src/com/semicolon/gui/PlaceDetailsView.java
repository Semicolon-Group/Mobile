package com.semicolon.gui;

import com.codename1.components.InteractionDialog;
import com.codename1.components.ToastBar;
import com.codename1.googlemaps.MapContainer;
import com.codename1.io.File;
import com.codename1.io.FileSystemStorage;
import com.codename1.io.Storage;
import com.codename1.javascript.JSObject;
import com.codename1.javascript.JavascriptContext;
import com.codename1.maps.Coord;
import com.codename1.ui.BrowserComponent;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.geom.Rectangle;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import com.pofper.maps.entity.Place;
import com.semicolon.mysoulmate.MyApplication;

public class PlaceDetailsView {
    private final Place place;
    private final Form form;
    private final Form parentForm;
    
    public PlaceDetailsView(Place place, Form parentForm){
        this.place = place;
        this.form = new Form();
        this.parentForm = parentForm;
        this.form.getToolbar().addCommandToLeftBar(
                "Back", 
                MyApplication.theme.getImage("back-command.png"), 
                (ev) -> parentForm.showBack());
        buildForm();
    }
    
    public void buildForm(){
        Container container = new Container(BoxLayout.y());
        BrowserComponent bc = new BrowserComponent();
        bc.setPreferredH(200);
        
        bc.addWebEventListener("onLoad", new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                JavascriptContext context = new JavascriptContext(bc);
                JSObject window = context.getWindow();
                window.call("initialize", new Object[]{
                    36.872530,
                    10.316018,
                    place.getAddress().getLocation().getLatitude(),
                    place.getAddress().getLocation().getLongitude()
                });
            }
        });
        
        container.add(bc);
        form.add(container);
        bc.setURL("jar:///assets/MapsView.html");
        String[] params = new String[2];
        params[0] = "9";
        params[1] = "10";
    }
    
    
    public static String getRootPath(String fileName) {
        String[] roots = FileSystemStorage.getInstance().getRoots();
        String root = roots[0];
        for (int i = 0; i < roots.length; i++) {
            if (FileSystemStorage.getInstance().getRootType(roots[i]) == FileSystemStorage.ROOT_TYPE_UNKNOWN) {
                root = roots[i];
                break;
            }
        }
        return root + FileSystemStorage.getInstance().getFileSystemSeparator() + fileName;
    }
    
    public Form getForm(){
        return form;
    }
}
