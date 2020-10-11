package com.example.agendamento_online;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import com.example.agendamento_online.Paciente.Fragment.AgendaFragment;
import com.example.agendamento_online.Paciente.Fragment.HomePacienteFragment;
import com.example.agendamento_online.Paciente.Fragment.SobreFragment;
import com.example.agendamento_online.authentication.Conexao;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    FirebaseAuth auth;
    FirebaseUser user;
    FirebaseDatabase database;
    DatabaseReference databaseReference;

    DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        if(savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomePacienteFragment()).commit();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_agenda:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AgendaFragment()).commit();
                break;
            case R.id.nav_index:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomePacienteFragment()).commit();
                break;
//            case R.id.nav_perfil:
//                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomePacienteFragment()).commit();
//                break;
            case R.id.nav_about:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SobreFragment()).commit();
                break;
            case R.id.nav_sair:
                Conexao.logOut();
                Intent voltar = new Intent(this,MainActivity.class);
                startActivity(voltar);
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer((GravityCompat.START));
        } else {
            super.onBackPressed();
        }
    }


    protected void onStart() {
        super.onStart();
        auth = Conexao.getFirebaseAuth();
        user = Conexao.getFirebaseUser();
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();

        verificarUser();
    }

    private void verificarUser() {

        if (user == null) {
            Intent principal = new Intent(Home.this, MainActivity.class);
            startActivity(principal);
        }
    }

}