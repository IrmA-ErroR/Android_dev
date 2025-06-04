package com.mirea.kabanovasvetlana.lesson6;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SuperheroDB extends AppCompatActivity {
    private SuperheroDao superheroDao;
    private EditText nameEditText, powerEditText, originEditText, idEditText;
    private TextView listTextView;
    private Button addButton, deleteButton, showButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_superhero_db);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        AppDatabase db = App.getInstance().getDatabase();
        superheroDao = db.superheroDao();

        nameEditText = findViewById(R.id.editTextName);
        powerEditText = findViewById(R.id.editTextPower);
        originEditText = findViewById(R.id.editTextOrigin);
        idEditText = findViewById(R.id.editTextId);

        listTextView = findViewById(R.id.superheroList);

        addButton = findViewById(R.id.buttonAdd);
        deleteButton = findViewById(R.id.buttonDelete);
        showButton = findViewById(R.id.buttonShowAll);

        // Добавить героя
        addButton.setOnClickListener(v -> {
            Superhero hero = new Superhero();
            hero.name = nameEditText.getText().toString();
            hero.superpower = powerEditText.getText().toString();
            hero.origin = originEditText.getText().toString();

            superheroDao.insert(hero);
            clearInputs();
        });

        // Удалить героя по ID
        deleteButton.setOnClickListener(v -> {
            String idText = idEditText.getText().toString();
            if (!idText.isEmpty()) {
                long id = Long.parseLong(idText);
                Superhero hero = superheroDao.getById(id);
                if (hero != null) {
                    superheroDao.delete(hero);
                }
                clearInputs();
            }
        });

        // Показать всех героев
        showButton.setOnClickListener(v -> {
            List<Superhero> heroes = superheroDao.getAll();
            StringBuilder sb = new StringBuilder();
            for (Superhero hero : heroes) {
                sb.append("ID: ").append(hero.id).append("\n")
                        .append("Имя: ").append(hero.name).append("\n")
                        .append("Суперсила: ").append(hero.superpower).append("\n")
                        .append("Происхождение: ").append(hero.origin).append("\n\n");
            }
            listTextView.setText(sb.toString());
        });
    }

    private void clearInputs() {
        nameEditText.setText("");
        powerEditText.setText("");
        originEditText.setText("");
        idEditText.setText("");
    }
}