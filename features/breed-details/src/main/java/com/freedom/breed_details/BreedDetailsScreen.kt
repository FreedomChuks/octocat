package com.freedom.breed_details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.freedom.designsystem.theme.OctocatTheme
import com.freedom.designsystem.theme.White
import com.freedom.designsystem.theme.textGray
import com.freedom.model.BreedModel
import com.freedom.model.CatModel
import com.freedom.model.Weight


@Composable
fun BreedDetailScreen(
    catModel: CatModel,
    onBack: () -> Unit
) {
    val breed = catModel.breedModels.first()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
    ) {
        Box(
            Modifier.fillMaxWidth().height(350.dp)
        ) {
            AsyncImage(
                model = catModel.url,
                contentDescription = breed.name,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            IconButton(
                onClick = onBack,
                modifier = Modifier
                    .padding(16.dp)
                    .statusBarsPadding()
                    .background(color = MaterialTheme.colorScheme.surface.copy(alpha = 0.5f), shape = CircleShape)


            ) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = White
                )
            }
        }


        Column(Modifier.fillMaxWidth().padding(20.dp)) {
            Text(
                breed.name,
                style = MaterialTheme.typography.displayMedium.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(Modifier.height(16.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Place,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp),
                    tint = Color(0xFFE53935)
                )
                Spacer(Modifier.width(4.dp))
                Text(
                    text = breed.origin,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            Spacer(Modifier.height(24.dp))

            Text(
                "Description",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(Modifier.height(8.dp))
            Text(
                breed.description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(Modifier.height(24.dp))


            Text(
                "Quick Info",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(Modifier.height(16.dp))

            val quickInfo = buildList<Pair<String, String>> {
                add("Life Span" to "${breed.lifeSpan} yrs")
                add("Weight" to "${breed.weight.metric} kg")
                add("Temperament" to (breed.temperament
                    .split(",")
                    .firstOrNull()
                    ?: breed.temperament)
                )
                add("Adaptability" to "${breed.adaptability}/5")
                add("Affection" to "${breed.affectionLevel}/5")
                add("Child Friendly" to "${breed.childFriendly}/5")
                add("Dog Friendly" to "${breed.dogFriendly}/5")
                add("Energy" to "${breed.energyLevel}/5")
                add("Grooming" to "${breed.grooming}/5")
                add("Intelligence" to "${breed.intelligence}/5")
                add("Shedding" to "${breed.sheddingLevel}/5")
                add("Social Needs" to "${breed.socialNeeds}/5")
                if (breed.hypoallergenic == 1) add("Hypoallergenic" to "Yes")
                if (breed.rare == 1) add("Rare" to "Yes")
            }

            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                maxItemsInEachRow = 3 // This was already 3, keeping as is.
            ) {
                quickInfo.forEach { (label, value) ->
                    QuickInfoCard(
                        modifier = Modifier.weight(1f),
                        label = label,
                        value = value,
                    )
                }
            }
        }
    }
}

@Composable
private fun QuickInfoCard(
    modifier: Modifier = Modifier,
    label: String,
    value: String,
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        modifier = modifier
            .height(80.dp)
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                value,
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                color =  MaterialTheme.colorScheme.primary,
                maxLines = 2
            )
            Text(
                label,
                style = MaterialTheme.typography.labelSmall,
                color = textGray
            )
        }
    }
}


@Preview(showBackground = true, backgroundColor = 0xFF0E1633)
@Composable
fun PreviewBreedDetailsScreen() {
    OctocatTheme { // Wrap with MaterialTheme for previews to see theming
        BreedDetailScreen(
            onBack = { /*TODO*/ },
            catModel = CatModel(
                id = "birm", // Using Birman example
                url = "https://cdn2.thecatapi.com/images/HOrX5gwLS.jpg", // Example image for Birman
                width = 1526,
                height = 2111,
                breedModels = listOf(
                    BreedModel(
                        weight = Weight(imperial = "6 - 15", metric = "3 - 7"),
                        id = "birm",
                        name = "Birman",
                        temperament = "Affectionate, Active, Gentle, Social",
                        origin = "France",
                        countryCodes = "FR",
                        description = "The Birman is a docile, quiet cat who loves people and will follow them from room to room. Expect the Birman to want to be involved in what you’re doing. He communicates in a soft voice, mainly to remind you that perhaps it’s time for dinner or maybe for a nice cuddle on the sofa. He enjoys being held and will relax in your arms like a furry baby.",
                        lifeSpan = "14 - 15",
                        indoor = 0,
                        lap = 1,
                        adaptability = 5,
                        affectionLevel = 5,
                        childFriendly = 4,
                        dogFriendly = 5,
                        energyLevel = 3,
                        grooming = 2,
                        healthIssues = 1,
                        intelligence = 3,
                        sheddingLevel = 3,
                        socialNeeds = 4,
                        strangerFriendly = 3,
                        vocalisation = 1,
                        experimental = 0,
                        hairless = 0,
                        natural = 0,
                        rare = 0,
                        rex = 0,
                        suppressedTail = 0,
                        shortLegs = 0,
                        hypoallergenic = 0,
                    )
                )
            )
        )
    }
}
