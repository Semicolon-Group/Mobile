package com.semicolon.mysoulmate;


import static com.codename1.ui.CN.*;
import com.codename1.ui.Form;
import com.codename1.ui.Dialog;
import com.codename1.ui.Label;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.codename1.io.Log;
import com.codename1.ui.Container;
import com.codename1.ui.FontImage;
import com.codename1.ui.Image;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.pofper.maps.entity.Point;
import com.semicolon.gui.BlockListView;
import com.semicolon.gui.MatchingView;
import com.semicolon.gui.NewsfeedView;
import com.semicolon.gui.OtherProfileView;
import com.semicolon.gui.ProfileView;
import com.semicolon.gui.RecommandationsListView;
import com.semicolon.service.BlockService;
import com.semicolon.service.MemberService;

/**
 * This file was generated by <a href="https://www.codenameone.com/">Codename One</a> for the purpose 
 * of building native mobile applications using Java.
 */
public class MyApplication {

    private Form current;
    public static Resources theme;
    public static int onlineId = 6;
    public static Form firstForm;
    public static int MemberId = 6;

    public void init(Object context) {
        theme = UIManager.initFirstTheme("/theme");

        // Enable Toolbar on all Forms by default
        Toolbar.setGlobalToolbar(true);

        // Pro only feature
        Log.bindCrashProtection(true);
    }
    
    public void start() {
        if(current != null){
            current.show();
            return;
        }
        firstForm = (new NewsfeedView()).getForm();

        firstForm.show();
    }

    public void stop() {
        current = getCurrentForm();
        if(current instanceof Dialog) {
            ((Dialog)current).dispose();
            current = getCurrentForm();
        }
    }
    
    public void destroy() {
    }

    public static void sideBar(Form form){
	Toolbar tb = form.getToolbar();
        Image icon = theme.getImage("icon.png");
        Container topBar = BorderLayout.east(new Label(icon));
        topBar.add(BorderLayout.SOUTH, new Label("MySoulmate", "SidemenuTagline"));
        topBar.setUIID("SideCommand");
        tb.addComponentToSideMenu(topBar);

	tb.addMaterialCommandToSideMenu("Newsfeed", FontImage.MATERIAL_LIST, e -> {
            new NewsfeedView().getForm().show();
        });
	tb.addMaterialCommandToSideMenu("Matching", FontImage.MATERIAL_SEARCH, e -> {
            new MatchingView().getForm().show();
        });
        tb.addMaterialCommandToSideMenu("Profile", FontImage.MATERIAL_ACCOUNT_CIRCLE, e -> {
            Form profileForm = (new ProfileView(form, MemberId)).getContainer();
            profileForm.show();
        });
        tb.addMaterialCommandToSideMenu("Suggestions", FontImage.MATERIAL_LIST, e -> {
            Form recommandationListForm = (new RecommandationsListView(3000, 200, 200, form)).getContainer();
            recommandationListForm.show();
        });
        tb.addMaterialCommandToSideMenu("Blocks", FontImage.MATERIAL_BLOCK, e -> {
            (new BlockListView(form, MemberId)).getForm().show();
        });
        tb.addMaterialCommandToSideMenu("Logout", FontImage.MATERIAL_EXIT_TO_APP, e -> {});
    }
}
