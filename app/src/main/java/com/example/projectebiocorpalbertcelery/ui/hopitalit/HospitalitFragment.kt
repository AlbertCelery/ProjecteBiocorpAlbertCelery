package com.example.projectebiocorpalbertcelery.ui.hopitalit


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.projectebiocorpalbertcelery.R
import com.example.projectebiocorpalbertcelery.data.DatabaseManager
import com.example.projectebiocorpalbertcelery.databinding.FragmentHospitalitBinding
import com.example.projectebiocorpalbertcelery.ui.home.MainActivity


class HospitalitFragment : Fragment() {
    private var _binding: FragmentHospitalitBinding? = null
    private val binding get() = _binding!!
    private lateinit var databaseManager: DatabaseManager
    private var dni: String = ""
    private var idHospitalitzacio: Int = 0
    private var idTractament: Int = 0
    private val sharedViewModel: HospitalitFragmentViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHospitalitBinding.inflate(layoutInflater, container, false)

        databaseManager = DatabaseManager()
        initHosp()
        //databaseManager.openDatabase(requireContext())

        val optionsMotiv: String = "SELECT nom FROM motivhospit"
        val nombres: MutableList<String> = databaseManager.obtenerNombres(optionsMotiv)
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, nombres)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerMotiuHosp.adapter = adapter
        binding.nouMotivBtn.setOnClickListener {
            val dialog = MotivPopupDialog()
            dialog.show(parentFragmentManager, "MotivPopupDialog")
        }
        binding.previousHospitalitBtn.setOnClickListener {
            previousHospitalit()
        }
        binding.nextHospitalitBtn.setOnClickListener {
            nextHospitalit()
        }
        binding.previousTractamentsBtn.setOnClickListener {
            previousTractaments()
        }
        binding.nextTractamentsBtn.setOnClickListener {
            nextTractaments()
        }
        binding.saveHospitalitBtn.setOnClickListener {
            saveHospitalitzacio()
        }
        binding.newEntryHospitalitBtn.setOnClickListener {
            clearHospitalitzacio()
            clearTractament()
        }
        binding.infoTractBtn1.setOnClickListener {
            findNavController().navigate(R.id.medicacioFragment)
        }
        binding.infoTractBtn2.setOnClickListener {
            findNavController().navigate(R.id.medicacioFragment)
        }
        binding.infoTractBtn3.setOnClickListener {
            findNavController().navigate(R.id.medicacioFragment)
        }
        binding.infoTractBtn4.setOnClickListener {
            findNavController().navigate(R.id.medicacioFragment)
        }
        binding.newEntryTractHospBtn.setOnClickListener {
            clearTractament()

        }
        binding.saveTractHospBtn.setOnClickListener {
            saveTractament()
        }

        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedViewModel.loadHospitalitTrigger.observe(viewLifecycleOwner) { dni ->
            loadHospitalitzacioNewPacient(dni)
        }
    }
    fun clearHospitalitzacio(){

        binding.dataFiHospEdit.text.clear()
        binding.dataFiHospEdit.text.clear()
        binding.tempsTotalHospValue.text = ""
        binding.HospitalEdit.text.clear()
        binding.spinnerMotiuHosp.setSelection(0)

    }
    fun clearTractament(){
        binding.spinnerMedicamentTract1.setSelection(0)
        binding.spinnerMedicamentTract2.setSelection(0)
        binding.spinnerMedicamentTract3.setSelection(0)
        binding.spinnerMedicamentTract4.setSelection(0)
        binding.horesTractEdit1.text?.clear()
        binding.horesTractEdit2.text?.clear()
        binding.horesTractEdit3.text?.clear()
        binding.horesTractEdit4.text?.clear()
        binding.iniciTractEdit.text?.clear()
        binding.fiTractEdit.text?.clear()
        binding.tempsTotalTractEdit.text = ""
    }
    fun loadHospitalitzacio(){
        clearHospitalitzacio()
        clearTractament()
        val nomHosp= databaseManager.getnomHosp(idHospitalitzacio)
        val iniciHosp = databaseManager.getIniciHospitalit(idHospitalitzacio)
        val fiHosp = databaseManager.getFiHospitalit(idHospitalitzacio)
        val tempstotal = databaseManager.getTotalDays(iniciHosp, fiHosp)
        var diestempstotal = ""
        val motivHosp = databaseManager.getMotiusHosp(idHospitalitzacio)
        if (tempstotal != 1) {
            diestempstotal = buildString {
                append(tempstotal)
                append(" dies")
            }
        } else {
            diestempstotal = buildString {
                append(tempstotal)
                append(" dia")
            }
        }
        binding.HospitalEdit.setText(nomHosp)
        binding.dataIniciHospEdit.setText(iniciHosp)
        binding.dataFiHospEdit.setText(fiHosp)
        binding.tempsTotalHospValue.text = diestempstotal
        binding.spinnerMotiuHosp.setSelection(motivHosp)
        val firstMalaltiaTractamentId = databaseManager.getFirstMalaltiaTractament(idHospitalitzacio)
        if (firstMalaltiaTractamentId != null) {
            idTractament = firstMalaltiaTractamentId
            val nomMedicament1 = databaseManager.getmedidHosp(idTractament)
            val nomMedicament2 = databaseManager.getmedid2Hosp(idTractament)
            val nomMedicament3 = databaseManager.getmedid3Hosp(idTractament)
            val nomMedicament4 = databaseManager.getmedid4Hosp(idTractament)
            val horamed1 = databaseManager.gethoramed1Hosp(idTractament)
            val horamed2 = databaseManager.gethoramed2Hosp(idTractament)
            val horamed3 = databaseManager.gethoramed3Hosp(idTractament)
            val horamed4 = databaseManager.gethoramed4Hosp(idTractament)
            val iniciTractament = databaseManager.getIniciHospTract(idTractament)
            val fiTractament = databaseManager.getFiHospTract(idTractament)
            val tempsTractament = databaseManager.getMalaltiaTractDays(idTractament)
            var diestempstotal = ""
            if (tempsTractament != 1) {
                diestempstotal = buildString {
                    append(tempsTractament)
                    append(" dies")
                }
            } else {
                diestempstotal = buildString {
                    append(tempsTractament)
                    append(" dia")
                }
            }
            binding.tempsTotalTractEdit.text = diestempstotal
            binding.spinnerMedicamentTract1.setSelection(nomMedicament1)
            binding.spinnerMedicamentTract2.setSelection(nomMedicament2)
            binding.spinnerMedicamentTract3.setSelection(nomMedicament3)
            binding.spinnerMedicamentTract4.setSelection(nomMedicament4)
            binding.horesTractEdit1.setText(horamed1)
            binding.horesTractEdit2.setText(horamed2)
            binding.horesTractEdit3.setText(horamed3)
            binding.horesTractEdit4.setText(horamed4)
            binding.iniciTractEdit.setText(iniciTractament)
            binding.fiTractEdit.setText(fiTractament)

        }else{
            idTractament = 0
        }
        updateBtn()

    }
    fun updateBtn(){
        binding.previousHospitalitBtn.isEnabled = (idHospitalitzacio > databaseManager.getFirstHospitalitzacio(dni)!!) && (idHospitalitzacio != 0)
        binding.nextHospitalitBtn.isEnabled = (idHospitalitzacio < databaseManager.getLastHospitalitzacio(dni)!!) && (idHospitalitzacio != 0)
        binding.nextTractamentsBtn.isEnabled = (idTractament < databaseManager.getLastHospitalitzacioTract(idHospitalitzacio)!!) && (idTractament != 0)
        binding.previousTractamentsBtn.isEnabled = (idTractament > databaseManager.getFirstHospitalitzacioTract(idHospitalitzacio)!!) && (idTractament != 0)

    }
    fun loadHospitalitzacioNewPacient(dni: String){
        this.dni = dni
        clearHospitalitzacio()
        clearTractament()
        val firstHospitalitzacioId = databaseManager.getFirstHospitalitzacio(dni)
        if (firstHospitalitzacioId != null) {
            val nomHospitalitzacio = databaseManager.getnomHosp(firstHospitalitzacioId)
            val iniciHospitalitzacio = databaseManager.getIniciHospitalit(firstHospitalitzacioId)
            val fiHospitalitzacio = databaseManager.getFiHospitalit(firstHospitalitzacioId)
            val tempstotal = databaseManager.getTotalDays(iniciHospitalitzacio, fiHospitalitzacio)
            val motivHosp = databaseManager.getMotiusHosp(firstHospitalitzacioId) - 1
            var diestempstotal = ""
            if (tempstotal != 1) {
                diestempstotal = buildString {
                    append(tempstotal)
                    append(" dies")
                }
            } else {
                diestempstotal = buildString {
                    append(tempstotal)
                    append(" dia")
                }
            }
            binding.HospitalEdit.setText(nomHospitalitzacio)
            binding.dataIniciHospEdit.setText(iniciHospitalitzacio)
            binding.dataFiHospEdit.setText(fiHospitalitzacio)
            binding.tempsTotalHospValue.setText(diestempstotal)
            binding.spinnerMotiuHosp.setSelection(motivHosp)
            idHospitalitzacio = firstHospitalitzacioId
            val firstMalaltiaTractamentId = databaseManager.getFirstMalaltiaTractament(idHospitalitzacio)
            if (firstMalaltiaTractamentId != null) {
                idTractament = firstMalaltiaTractamentId
                val nomMedicament1 = databaseManager.getmedidHosp(idTractament)-1
                val nomMedicament2 = databaseManager.getmedid2Hosp(idTractament)-1
                val nomMedicament3 = databaseManager.getmedid3Hosp(idTractament)-1
                val nomMedicament4 = databaseManager.getmedid4Hosp(idTractament)-1
                val horamed1 = databaseManager.gethoramed1Hosp(idTractament)
                val horamed2 = databaseManager.gethoramed2Hosp(idTractament)
                val horamed3 = databaseManager.gethoramed3Hosp(idTractament)
                val horamed4 = databaseManager.gethoramed4Hosp(idTractament)
                val iniciTractament = databaseManager.getIniciHospTract(idTractament)
                val fiTractament = databaseManager.getFiHospTract(idTractament)
                val tempsTractament = databaseManager.getMalaltiaTractDays(idTractament)
                var diestempstotal = ""
                if (tempsTractament != 1) {
                    diestempstotal = buildString {
                        append(tempsTractament)
                        append(" dies")
                    }
                } else {
                    diestempstotal = buildString {
                        append(tempsTractament)
                        append(" dia")
                    }

                }
                binding.tempsTotalTractEdit.text = diestempstotal
                binding.spinnerMedicamentTract1.setSelection(nomMedicament1)
                binding.spinnerMedicamentTract2.setSelection(nomMedicament2)
                binding.spinnerMedicamentTract3.setSelection(nomMedicament3)
                binding.spinnerMedicamentTract4.setSelection(nomMedicament4)
                binding.horesTractEdit1.setText(horamed1)
                binding.horesTractEdit2.setText(horamed2)
                binding.horesTractEdit3.setText(horamed3)
                binding.horesTractEdit4.setText(horamed4)
                binding.iniciTractEdit.setText(iniciTractament)
                binding.fiTractEdit.setText(fiTractament)





            }else{
                idTractament = 0
            }

        }else{
            idHospitalitzacio = 0
        }
        updateBtn()
    }
    fun loadTractament(){
        clearTractament()
        val nomMedicament1 = databaseManager.getmedidHosp(idTractament)
        val nomMedicament2 = databaseManager.getmedid2Hosp(idTractament)
        val nomMedicament3 = databaseManager.getmedid3Hosp(idTractament)
        val nomMedicament4 = databaseManager.getmedid4Hosp(idTractament)
        val horamed1 = databaseManager.gethoramed1Hosp(idTractament)
        val horamed2 = databaseManager.gethoramed2Hosp(idTractament)
        val horamed3 = databaseManager.gethoramed3Hosp(idTractament)
        val horamed4 = databaseManager.gethoramed4Hosp(idTractament)
        val iniciTractament = databaseManager.getIniciHospTract(idTractament)
        val fiTractament = databaseManager.getFiHospTract(idTractament)
        val tempsTractament = databaseManager.getMalaltiaTractDays(idTractament)
        var diestempstotal = ""
        if (tempsTractament != 1) {
            diestempstotal = buildString {
                append(tempsTractament)
                append(" dies")
            }
        }else{
            diestempstotal = buildString {
                append(tempsTractament)
                append(" dia")
            }

        }
        binding.tempsTotalTractEdit.text = diestempstotal
        binding.spinnerMedicamentTract1.setSelection(nomMedicament1)
        binding.spinnerMedicamentTract2.setSelection(nomMedicament2)
        binding.spinnerMedicamentTract3.setSelection(nomMedicament3)
        binding.spinnerMedicamentTract4.setSelection(nomMedicament4)
        binding.horesTractEdit1.setText(horamed1)
        binding.horesTractEdit2.setText(horamed2)
        binding.horesTractEdit3.setText(horamed3)
        binding.horesTractEdit4.setText(horamed4)
        binding.iniciTractEdit.setText(iniciTractament)
        binding.fiTractEdit.setText(fiTractament)
        updateBtn()


    }
    fun initHosp(){
        val optionsMedicament: String = "SELECT nom FROM medicament"
        val nombres: MutableList<String> = databaseManager.obtenerNombres(optionsMedicament)
        val adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, nombres)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerMedicamentTract1.adapter = adapter
        binding.spinnerMedicamentTract2.adapter = adapter
        binding.spinnerMedicamentTract3.adapter = adapter
        binding.spinnerMedicamentTract4.adapter = adapter

    }
    fun previousHospitalit(){
        val nextHospId = databaseManager.getPreviousHospitalitzacio(dni, idHospitalitzacio)
        if (nextHospId != null) {
            if (nextHospId.moveToFirst()) {
                this.idHospitalitzacio = nextHospId.getInt(0)
                loadHospitalitzacio()
            }

        }
    }
    fun nextHospitalit(){
        val nextHospId = databaseManager.getNextHospitalitzacio(dni, idHospitalitzacio)
        if (nextHospId != null) {
            if (nextHospId.moveToFirst()) {
                this.idHospitalitzacio = nextHospId.getInt(0)
                loadHospitalitzacio()
            }
        }
    }
    fun previousTractaments(){
        val nextTractamentId = databaseManager.getPreviousHospitalitzacioTract(idHospitalitzacio, idTractament)
        if (nextTractamentId != null) {
            if (nextTractamentId.moveToFirst()) {
                this.idTractament = nextTractamentId.getInt(0)
                loadTractament()
            }
        }
    }
    fun nextTractaments() {
        val nextTractamentId =
            databaseManager.getNextHospitalitzacioTract(idHospitalitzacio, idTractament)
        if (nextTractamentId != null) {
            if (nextTractamentId.moveToFirst()) {
                this.idTractament = nextTractamentId.getInt(0)
                loadTractament()
            }
        }
    }
        fun saveHospitalitzacio() {
            val inici = binding.dataIniciHospEdit.text.toString()
            val fi = binding.dataFiHospEdit.text.toString()
            val motiv = binding.spinnerMotiuHosp.selectedItemPosition + 1
            val dni = dni
            val nomHosp = binding.HospitalEdit.text.toString()
            databaseManager.insertDataHospitalitzacio(inici, fi, nomHosp, motiv, dni)
            clearHospitalitzacio()
            clearTractament()

        }
    fun saveTractament() {
            val inici = binding.iniciTractEdit.text.toString()
            val fi = binding.fiTractEdit.text.toString()
            val horamed1 = binding.horesTractEdit1.text.toString()
            val horamed2 = binding.horesTractEdit2.text.toString()
            val horamed3 = binding.horesTractEdit3.text.toString()
            val horamed4 = binding.horesTractEdit4.text.toString()
            val nomMedicament1 = binding.spinnerMedicamentTract1.selectedItemPosition
            val nomMedicament2 = binding.spinnerMedicamentTract2.selectedItemPosition
            val nomMedicament3 = binding.spinnerMedicamentTract3.selectedItemPosition
            val nomMedicament4 = binding.spinnerMedicamentTract4.selectedItemPosition
            databaseManager.insertDataTractamentHosp(
                idHospitalitzacio,
                horamed1,
                horamed2,
                horamed3,
                horamed4,
                nomMedicament1 + 1,
                nomMedicament2 + 1,
                nomMedicament3 + 1,
                nomMedicament4 + 1,
                inici,
                fi
            )
            clearTractament()
    }


}