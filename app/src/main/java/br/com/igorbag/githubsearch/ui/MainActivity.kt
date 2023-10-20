package br.com.igorbag.githubsearch.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import br.com.igorbag.githubsearch.R
import br.com.igorbag.githubsearch.data.GitHubService
import br.com.igorbag.githubsearch.domain.Repository

class MainActivity : AppCompatActivity() {

    lateinit var actvUsername: AutoCompleteTextView
    lateinit var btnSearch: Button
    lateinit var tvUsernames: TextView
    lateinit var rvListRepositories: RecyclerView
    lateinit var githubService: GitHubService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupView()
        updateTVUsers()
        setupListeners()
        showUserName()
        setupRetrofit()
        getAllReposByUserName()
        setupAutoCompleteSugestions()
    }

    /**
     * Recupera os componentes da tela
     */
    fun setupView() {
        btnSearch = findViewById(R.id.btn_search)
        actvUsername = findViewById(R.id.actv_username);
        tvUsernames = findViewById(R.id.tv_usernames)
        rvListRepositories = findViewById(R.id.rv_list_repositories)
    }

    /**
     * Configura os listeners de click em componentes da tela
     */
    private fun setupListeners() {
        btnSearch.setOnClickListener {
            saveUserLocal()
            updateTVUsers()
            getAllReposByUserName()
        }
    }

    /**
     * Recupera lista de usuarios salvos no SharedPreferences
     */
    fun getUsersLocal(): List<String> {
        val sharedPreferences = getPreferences(Context.MODE_PRIVATE) ?: return emptyList()
        val usernamesStr = sharedPreferences.getString(getString(R.string.pref_usernames), "")
        return usernamesStr?.split(",") ?: emptyList()
    }

    /**
     * Salva usuario preenchido no EditText em uma lista de usuários utilizando SharedPreferences
     */
    private fun saveUserLocal() {
        var usernamesList = getUsersLocal().toMutableList()
        usernamesList.add(actvUsername.text.toString())

        val usernamesStr = usernamesList.joinToString(",")

        val sharedPreferences = getPreferences(Context.MODE_PRIVATE) ?: return
        val editorPreferences = sharedPreferences.edit()
        editorPreferences.putString(getString(R.string.pref_usernames), usernamesStr)
        editorPreferences.apply()
    }

    private fun showUserName() {
        //@TODO 4- depois de persistir o usuario exibir sempre as informacoes no EditText  se a sharedpref possuir algum valor, exibir no proprio editText o valor salvo
    }

    private fun updateTVUsers() {
        tvUsernames.text = getUsersLocal().joinToString(", ").substring(2)
    }

    private fun setupAutoCompleteSugestions() {
        val suggestions = getUsersLocal()
        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, suggestions)
        actvUsername.setAdapter(adapter)
    }

    //Metodo responsavel por fazer a configuracao base do Retrofit
    fun setupRetrofit() {
        /*
           @TODO 5 -  realizar a Configuracao base do retrofit
           Documentacao oficial do retrofit - https://square.github.io/retrofit/
           URL_BASE da API do  GitHub= https://api.github.com/
           lembre-se de utilizar o GsonConverterFactory mostrado no curso
        */
    }

    //Metodo responsavel por buscar todos os repositorios do usuario fornecido
    fun getAllReposByUserName() {
        // TODO 6 - realizar a implementacao do callback do retrofit e chamar o metodo setupAdapter se retornar os dados com sucesso
    }

    // Metodo responsavel por realizar a configuracao do adapter
    fun setupAdapter(list: List<Repository>) {
        /*
            @TODO 7 - Implementar a configuracao do Adapter , construir o adapter e instancia-lo
            passando a listagem dos repositorios
         */
    }


    // Metodo responsavel por compartilhar o link do repositorio selecionado
    // @Todo 11 - Colocar esse metodo no click do share item do adapter
    fun shareRepositoryLink(urlRepository: String) {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, urlRepository)
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }

    // Metodo responsavel por abrir o browser com o link informado do repositorio

    // @Todo 12 - Colocar esse metodo no click item do adapter
    fun openBrowser(urlRepository: String) {
        startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse(urlRepository)
            )
        )

    }

}